package io.github.bszwej.core

import akka.actor.ActorRef
import com.typesafe.scalalogging.LazyLogging
import io.github.bszwej.core.TweetCollectorActor.{Error, NewTweet}
import twitter4j.{StallWarning, Status, StatusDeletionNotice, StatusListener}

trait ActorTweetListener extends LazyLogging {

  def tweetListener(actorRef: ActorRef): StatusListener = new StatusListener() {

    override def onStatus(status: Status): Unit =
      actorRef ! NewTweet(status.getUser.getName, status.getText)

    override def onStallWarning(warning: StallWarning): Unit = {}

    override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = {}

    override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = {}

    override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = {}

    override def onException(ex: Exception): Unit = actorRef ! Error(ex)
  }

}
