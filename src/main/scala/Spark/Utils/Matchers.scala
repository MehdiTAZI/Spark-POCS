package Spark.Utils

import twitter4j.Status

object Matchers {

   def filterOnKeywords(twitterStatus: Status, keyWordsList: List[String]): Boolean = {
    keyWordsList.exists(
      x => twitterStatus.getText.toLowerCase().contains(x) ||
        twitterStatus.getText.toLowerCase().startsWith(StringProcessing.ltrim(x)) ||
        twitterStatus.getText.toLowerCase().endsWith(StringProcessing.rtrim(x))
    )
  }

   def detailedMapping(twitterStatus: Status, keyWordsList: List[String]): String = {
    String.format("%s|%s|%s|%s|%s|%s",
      twitterStatus.getId.toString,
      new StringBuilder("@").append(twitterStatus.getUser.getScreenName).toString(),
      twitterStatus.getText,
      twitterStatus.getCreatedAt.toString,
      twitterStatus.getRetweetCount.toString,
      keyWordsList.filter(x => twitterStatus.getText.toLowerCase().contains(x) || twitterStatus.getText.toLowerCase().startsWith(StringProcessing.ltrim(x)) || twitterStatus.getText.toLowerCase().endsWith(StringProcessing.rtrim(x)))
    )
  }

   def abstractMapping(twitterStatus: Status): String = {
    String.format("%s|%s",
      new StringBuilder("@").append(twitterStatus.getUser.getScreenName).toString(),
      twitterStatus.getText
    )
  }

  def countByTag(tweet : String) = {

  }

}
