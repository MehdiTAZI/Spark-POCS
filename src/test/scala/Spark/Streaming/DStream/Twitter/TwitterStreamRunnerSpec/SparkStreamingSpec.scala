package Spark.Streaming.DStream.Twitter.TwitterStreamRunnerSpec

import java.nio.file.Files

import Spark.Streaming.DStream.Twitter.TweetsReceiver
import Spark.Streaming.DStream.Twitter.TwitterStreamRunner.filterOnKeywords
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext, Time}
import org.scalatest._
import org.mockito.Mockito.mock
import twitter4j.Status

class SparkStreamingSpec extends FunSuite with BeforeAndAfterEach{

  var sparkStreamingContext : StreamingContext = _
  var config : SparkConf = _
  var frequency : Long = 1
  val appName = "twitter test application"

  override def beforeEach() {

    config = new SparkConf().setAppName(appName).setMaster("local[2]")

    sparkStreamingContext = new StreamingContext(config, Seconds(frequency))

    val checkpointDir = Files.createTempDirectory(new StringBuilder(appName).append("_checkpoint").toString()).toString
    sparkStreamingContext.checkpoint(checkpointDir)

    sparkStreamingContext.start();
  }

  test("mapping tweets "){

    val mockedStreamingContext : StreamingContext = mock(classOf[StreamingContext])

    //when(mockedStreamingContext.stream("localhost", 9999))

    //val lines = mockedStreamingContext.socketTextStream("localhost", 9999)


  }

  override def afterEach() {
    if (sparkStreamingContext != null) {
      sparkStreamingContext.stop()
      sparkStreamingContext = null
    }

  }

}
