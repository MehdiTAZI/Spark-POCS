package Spark.Streaming.DStream.Twitter


import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import twitter4j._

//Cache with two replicate on memory and disk
abstract class TwitterListeners extends Receiver[Status](StorageLevel.MEMORY_AND_DISK_2){

    def statusListener = new StatusListener() {
      def onStatus(status: Status) { store(status) }
      def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {store(TwitterStatusAdapter.toStatus(statusDeletionNotice))}
      def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
      def onException(e: Exception) { e.printStackTrace }
      def onScrubGeo(arg0: Long, arg1: Long) {}
      def onStallWarning(warning: StallWarning) {}
  }

   def userStreamListener = new UserStreamListener() {
     override def onDeletionNotice(directMessageId: Long, userId: Long): Unit = ???

     override def onFriendList(friendIds: Array[Long]): Unit = ???

     override def onFavorite(source: User, target: User, favoritedStatus: Status): Unit = ???

     override def onUnfavorite(source: User, target: User, unfavoritedStatus: Status): Unit = ???

     override def onFollow(source: User, followedUser: User): Unit = ???

     override def onUnfollow(source: User, unfollowedUser: User): Unit = ???

     override def onDirectMessage(directMessage: DirectMessage): Unit = ???

     override def onUserListMemberAddition(addedMember: User, listOwner: User, list: UserList): Unit = ???

     override def onUserListMemberDeletion(deletedMember: User, listOwner: User, list: UserList): Unit = ???

     override def onUserListSubscription(subscriber: User, listOwner: User, list: UserList): Unit = ???

     override def onUserListUnsubscription(subscriber: User, listOwner: User, list: UserList): Unit = ???

     override def onUserListCreation(listOwner: User, list: UserList): Unit = ???

     override def onUserListUpdate(listOwner: User, list: UserList): Unit = ???

     override def onUserListDeletion(listOwner: User, list: UserList): Unit = ???

     override def onUserProfileUpdate(updatedUser: User): Unit = ???

     override def onUserSuspension(suspendedUser: Long): Unit = ???

     override def onUserDeletion(deletedUser: Long): Unit = ???

     override def onBlock(source: User, blockedUser: User): Unit = ???

     override def onUnblock(source: User, unblockedUser: User): Unit = ???

     override def onRetweetedRetweet(source: User, target: User, retweetedStatus: Status): Unit = ???

     override def onFavoritedRetweet(source: User, target: User, favoritedRetweeet: Status): Unit = ???

     override def onQuotedTweet(source: User, target: User, quotingTweet: Status): Unit = ???

     override def onStatus(status: Status): Unit =  {store(status) }

     override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = {store(TwitterStatusAdapter.toStatus(statusDeletionNotice))}

     override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = ???

     override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = ???

     override def onStallWarning(warning: StallWarning): Unit = ???

     override def onException(ex: Exception): Unit = ???
   }
}
