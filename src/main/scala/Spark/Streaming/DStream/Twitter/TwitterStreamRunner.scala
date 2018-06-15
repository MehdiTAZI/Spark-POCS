package Spark.Streaming.DStream.Twitter

import Spark.Utils.StringProcessing
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext, Time}
import twitter4j.Status;
import java.nio.file.Files

object TwitterStreamRunner {

  private val appsList = collection.mutable.Map[String, StreamingContext]()

  private def getSavePath(output_path: String, nature: String, time: Time): String = {
    return new StringBuilder(output_path).append(time.milliseconds.toString).append("/").append(nature).toString()
  }

  private def filterOnKeywords(twitterStatus: Status, keyWordsList: List[String]): Boolean = {
    keyWordsList.exists(
      x => twitterStatus.getText.toLowerCase().contains(x) ||
        twitterStatus.getText.toLowerCase().startsWith(StringProcessing.ltrim(x)) ||
        twitterStatus.getText.toLowerCase().endsWith(StringProcessing.rtrim(x))
    )
  }

  private def detailedMapping(twitterStatus: Status, keyWordsList: List[String]): String = {
    String.format("%s|%s|%s|%s|%s|%s",
      twitterStatus.getId.toString,
      new StringBuilder("@").append(twitterStatus.getUser.getScreenName).toString(),
      twitterStatus.getText,
      twitterStatus.getCreatedAt.toString,
      twitterStatus.getRetweetCount.toString,
      keyWordsList.filter(x => twitterStatus.getText.toLowerCase().contains(x) || twitterStatus.getText.toLowerCase().startsWith(StringProcessing.ltrim(x)) || twitterStatus.getText.toLowerCase().endsWith(StringProcessing.rtrim(x)))
    )
  }

  private def abstractMapping(twitterStatus: Status): String = {
    String.format("%s|%s",
      new StringBuilder("@").append(twitterStatus.getUser.getScreenName).toString(),
      twitterStatus.getText
    )
  }

  // todo : to fix
  def stop(appName: String): Unit = {
    if (appsList.contains(appName)) {
      var sparkStreamingContext = appsList.get(appName).get
      sparkStreamingContext.stop()
      sparkStreamingContext = null
      print("application requesting stop")
    }
  }

  def run(appName: String, keyWordsList: List[String], frequency: Int, tweetsOutputDirectory: String = "outputs/tweets") {

    val output_path = new StringBuilder(tweetsOutputDirectory).append("/").toString()

    if (!appsList.contains(appName)) {

      // setting master to local[2] in order to identify any problems that may only arise on distribute work.
      val config = new SparkConf().setAppName(appName).setMaster("local[2]")
      var sparkStreamingContext = new StreamingContext(config, Seconds(frequency))

      val checkpointDir = Files.createTempDirectory(new StringBuilder(appName).append("_checkpoint").toString()).toString
      sparkStreamingContext.checkpoint(checkpointDir)

      val hashTagListLowered = keyWordsList.map(x => x.toLowerCase())

      val tweetsDStream = sparkStreamingContext
        .receiverStream(new TweetsReceiver(appName, keyWordsList))
        .filter(s => filterOnKeywords(s, keyWordsList))

      // caching on memory otherwise on disk, used for detailed & abstract mapping
      tweetsDStream.persist(StorageLevel.MEMORY_AND_DISK)

      val tweetsDStreamDetailedMapping = tweetsDStream.map(s => detailedMapping(s, hashTagListLowered))

      val tweetsDStreamAbstractMapping = tweetsDStream.map(s => abstractMapping(s))

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
