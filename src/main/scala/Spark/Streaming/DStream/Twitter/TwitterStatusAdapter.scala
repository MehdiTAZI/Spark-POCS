package Spark.Streaming.DStream.Twitter

import java.util.Date

import twitter4j._

object TwitterStatusAdapter {
  def toStatus(statusDeletionNotice: StatusDeletionNotice): Status = {
    new Status() {
      override def getCreatedAt: Date = null

      override def getId: Long = statusDeletionNotice.getStatusId

      override def getText: String = null

      override def getSource: String = null

      override def isTruncated: Boolean = false

      override def getInReplyToStatusId: Long = 0

      override def getInReplyToUserId: Long = 0

      override def getInReplyToScreenName: String = null

      override def getGeoLocation: GeoLocation = null

      override def getPlace: Place = null

      override def isFavorited: Boolean = false

      override def isRetweeted: Boolean = false

      override def getFavoriteCount: Int = 0

      override def getUser: User = null

      override def isRetweet: Boolean = false

      override def getRetweetedStatus: Status = null

      override def getContributors: Array[Long] = null

      override def getRetweetCount: Int = 0

      override def isRetweetedByMe: Boolean = false

      override def getCurrentUserRetweetId: Long = 0

      override def isPossiblySensitive: Boolean = false

      override def getLang: String = null

      override def getScopes: Scopes = null

      override def getWithheldInCountries: Array[String] = null

      override def getQuotedStatusId: Long = 0

      override def getQuotedStatus: Status = null

      override def getRateLimitStatus: RateLimitStatus = null

      override def getAccessLevel: Int = 0

      override def compareTo(o: Status): Int = 0

      override def getUserMentionEntities: Array[UserMentionEntity] = null

      override def getURLEntities: Array[URLEntity] = null

      override def getHashtagEntities: Array[HashtagEntity] = null

      override def getMediaEntities: Array[MediaEntity] = null

      override def getExtendedMediaEntities: Array[ExtendedMediaEntity] = null

      override def getSymbolEntities: Array[SymbolEntity] = null
    }
  }
}
