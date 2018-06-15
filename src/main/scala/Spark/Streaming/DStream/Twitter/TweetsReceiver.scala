package Spark.Streaming.DStream.Twitter

import Spark.Utils.StringProcessing
import twitter4j.{FilterQuery, _}


class TweetsReceiver(appName: String, keyWordsList: List[String], language: String = "en") extends TwitterListeners {


  def onStart() {

    new Thread(new StringBuilder(appName).append(" stream listener").toString()) {
      override def run() {
        streamTweets()
      }
    }.start()

  }

  def onStop(): Unit = {

  }

  private def streamTweets() {

    /*val configurationBuilder = new twitter4j.conf.ConfigurationBuilder()
    configurationBuilder.setDebugEnabled(true)
    .setOAuthConsumerKey(Twitter4jConfiguration.CONSUMER_KEY)
    .setOAuthConsumerSecret(Twitter4jConfiguration.CONSUMER_SECRET)
    .setOAuthAccessToken(Twitter4jConfiguration.ACCESS_TOKEN)
    .setOAuthAccessTokenSecret(Twitter4jConfiguration.ACCESS_TOKEN_SECRET)*/

    val tweetsStream = new TwitterStreamFactory().getInstance()

    val filterQuery = new FilterQuery(0, null, keyWordsList.toArray)

    tweetsStream.addListener(statusListener)

    tweetsStream.filter(filterQuery)

    while (!isStopped) {}
    tweetsStream.cleanUp
    tweetsStream.shutdown
  }
}