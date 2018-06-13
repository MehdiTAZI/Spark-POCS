package Spark.Streaming.DStream.Twitter

import Spark.Utils.StringProcessing
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext, Time}


object TwitterStreamRunner {

  private val appsList = collection.mutable.Map[String, StreamingContext]()

  private def getSavePath(output_path: String, nature: String, time: Time): String = {
    return new StringBuilder(output_path).append(time.milliseconds.toString).append("/").append(nature).toString()
  }

  // todo : to fix
  def stop(appName: String): Unit = {
    if (appsList.contains(appName)) {
      val sparkStreamingContext = appsList.get(appName).get
      sparkStreamingContext.stop()
      print("application requesting stop")
    }
  }

  def run(appName: String, keyWordsList: List[String], frequency: Int, output: String = "outputs/tweets") {

    val output_path = new StringBuilder(output).append("/").toString()

    if (!appsList.contains(appName)) {

      val config = new SparkConf().setAppName(appName).setMaster("local[*]")
      val sparkStreamingContext = new StreamingContext(config, Seconds(frequency))

      val hashTagListLowered = keyWordsList.map(x => x.toLowerCase())

      val tweetsDStream = sparkStreamingContext.receiverStream(new TweetsReceiver(appName, keyWordsList))
        .filter(s =>
          hashTagListLowered.exists(x => s.getText.toLowerCase().contains(x) ||
            s.getText.toLowerCase().startsWith(StringProcessing.ltrim(x)) ||
            s.getText.toLowerCase().endsWith(StringProcessing.rtrim(x)))
        )

      // caching on memory otherwise on disk, used for detailed & abstract mapping
      tweetsDStream.persist(StorageLevel.MEMORY_AND_DISK)

      val tweetsDStreamDetailedMapping = tweetsDStream.map(
        s =>
          String.format("%s|%s|%s|%s|%s|%s",
            s.getId.toString,
            new StringBuilder("@").append(s.getUser.getScreenName).toString(),
            s.getText,
            s.getCreatedAt.toString,
            s.getRetweetCount.toString,
            hashTagListLowered.filter(x => s.getText.toLowerCase().contains(x) || s.getText.toLowerCase().startsWith(StringProcessing.ltrim(x)) || s.getText.toLowerCase().endsWith(StringProcessing.rtrim(x)))
          )
      )

      val tweetsDStreamAbstractMapping = tweetsDStream.map(
        s =>
          String.format("%s|%s",
            new StringBuilder("@").append(s.getUser.getScreenName).toString(),
            s.getText
          )
      )

      // avoid empty rdds, no need to do same manually for partitions, as this part will be done througth a cleaning & ingesting job
      tweetsDStreamDetailedMapping.foreachRDD(
        (rdd, time) => if (!rdd.isEmpty) rdd.saveAsTextFile(getSavePath(output_path, "detailed", time))
      )

      tweetsDStreamAbstractMapping.foreachRDD(
        (rdd, time) => if (!rdd.isEmpty) rdd.saveAsTextFile(getSavePath(output_path, "abstract", time))
      )


      sparkStreamingContext.start()

      appsList += (appName -> sparkStreamingContext)

      sparkStreamingContext.awaitTermination()

    }
    else {
      throw new Exception(new StringBuilder("The application ").append(appName).append(" already exists").toString())
    }

  }
}
