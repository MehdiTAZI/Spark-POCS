package Spark.Batch.RDD
import java.nio.file.{Files, Paths}
import java.io.{BufferedWriter, _}

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

object TweetsCleaner {

  def filterOnWithoutLink(content : String) : Boolean ={
     !(content.contains("http:") || content.contains("www."))
  }


  def run(appName: String, tweetsInputDirectory: String = "outputs/tweets/*/detailed",tweetsOutputDirectory: String="outputs/cleaned_tweets", masterConfig : String ="local[2]" ): Unit ={

    //Files.list(Paths.get(tweetsOutputDirectory)).forEach(f => Files.delete(f))
    //Files.delete(Paths.get(tweetsOutputDirectory))

    val config = new SparkConf().setAppName(appName).setMaster(masterConfig)
    var sparkContext = new SparkContext(config)
    val rdd = sparkContext.wholeTextFiles(tweetsInputDirectory)

    val filteredRDD = rdd.filter(x => filterOnWithoutLink(x._2))

    // todo :change the current separator to a more complex one in both ingestion and cleaning ([[--]] to avoid strings that contains |)
    // todo : to put into the Matcher
    val countByTag = filteredRDD.map(x => x._2)
    .map(_.split('|').lift(5).getOrElse(""))
      .filter(!_.isEmpty)
      .map(s => s.replaceAll("List|\\(|\\)|#",""))
      .flatMap(_.split(','))
      .map(e => if(e.contains("\n")) e.substring(0,e.indexOf("\n")) else e)
      .map(e => (e.trim,1))
      .reduceByKey((a,b) => a+b)

    countByTag.persist(StorageLevel.MEMORY_AND_DISK_2)

    val nbElem = countByTag.count()

    val tweetsCounter = sparkContext.doubleAccumulator("tweetsCounter")
    countByTag.foreach(t => tweetsCounter.add(t._2.toDouble))

    val file = new File(new StringBuilder(tweetsOutputDirectory).append("/result.csv").toString())
    val bufferedWriter = new BufferedWriter(new FileWriter(file))

    bufferedWriter.write("statistics :")
    bufferedWriter.newLine()
    bufferedWriter.newLine()

    bufferedWriter.write("nbElem : ")
    bufferedWriter.write(nbElem.toString)
    bufferedWriter.newLine()
    bufferedWriter.newLine()

    bufferedWriter.write("count : ")
    bufferedWriter.write(tweetsCounter.value.toInt.toString)
    bufferedWriter.newLine()
    bufferedWriter.newLine()

    bufferedWriter.write("elems : ")
    countByTag.collect().foreach({e =>
      bufferedWriter.write(e.toString())
      bufferedWriter.newLine()
    })

    bufferedWriter.close()

  }

}
