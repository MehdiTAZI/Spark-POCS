package Spark.Streaming.DStream.Twitter.TwitterStreamRunnerSpec

import Spark.Streaming.DStream.Twitter.TwitterStreamRunner
import org.mockito.Mockito._
import org.scalatest._
import twitter4j.Status


class TwitterStreamRunner extends FlatSpec with PrivateMethodTester{

  "The strings firstname & listname" should "not contains spaces on the left" in {
    val firstname = "  mehdi"

    //val twitterStatusMocked =  mock(classOf[Status])
    //when(twitterStatusMocked.getText()).thenReturn("this is a simple tweet with #spark hashtag")

    val transform = PrivateMethod[TwitterStreamRunner]('filterOnKeywords)

    //assert("" === invokePrivate transform(p1))

  }

}