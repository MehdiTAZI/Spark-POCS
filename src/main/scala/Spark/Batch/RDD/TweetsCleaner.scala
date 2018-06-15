package Spark.Batch.RDD
import java.nio.file.{Files, Paths}
import java.io.{BufferedWriter, _}

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

object TweetsCleaner {

  def filterOnWithoutLink(content : String) : Boolean ={
     !(content.contains("http:") || content.contains("www."))
  }


  def run(appName: String, tweetsInputDirectory: String = "outputs/tweets/*/detailed",tweetsOutputDirectory: String="outputs/cleaned_tweets"): Unit ={

    //Files.list(Paths.get(tweetsOutputDirectory)).forEach(f => Files.delete(f))
    //Files.delete(Paths.get(tweetsOutputDirectory))

    val config = new SparkConf().setAppName(appName).setMaster("local[2]")
    var sparkContext = new SparkContext(config)
    val rdd = sparkContext.wholeTextFiles(tweetsInputDirectory)

    val filteredRDD = rdd.filter(x => filterOnWithoutLink(x._2))
    filteredRDD.persist(StorageLevel.MEMORY_AND_DISK_2)

    val file = new File(new StringBuilder(tweetsOutputDirectory).append("/result.csv").toString())
    val bufferedWriter = new BufferedWriter(new FileWriter(file))

    bufferedWriter.write("statistics : \n\r")

    bufferedWriter.write("count : ")
    bufferedWriter.write(filteredRDD.count().toString)
    filteredRDD.map(x => x._2).map()

    bufferedWriter.close()


    filteredRDD.foreach(s => s._2)

  }

}
