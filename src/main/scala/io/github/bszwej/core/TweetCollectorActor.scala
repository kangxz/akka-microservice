package io.github.bszwej.core

import akka.actor.{Actor, ActorLogging, Props}
import io.github.bszwej.core.TweetCollectorActor.{Error, NewTweet}
import io.github.bszwej.core.model.Tweet
import io.github.bszwej.core.repository.TweetRepository
import twitter4j.{FilterQuery, TwitterStream}

object TweetCollectorActor {

  case class NewTweet(author: String, message: String)

  case class Error(exception: Exception)

  def props(hashtag: String, twitterStream: TwitterStream, tweetRepository: TweetRepository): Props =
    Props(new TweetCollectorActor(hashtag, twitterStream, tweetRepository))

}

class TweetCollectorActor(hashtag: String, twitterStream: TwitterStream, tweetRepository: TweetRepository)
  extends Actor with ActorLogging with ActorTweetListener {

  var lastTweetUser = None

  override def preStart(): Unit = {
    twitterStream.addListener(tweetListener(self))
    twitterStream.filter(new FilterQuery().track(s"#$hashtag"))
  }

  // TODO batch save to Mongo
  override def receive: Receive = {
    case NewTweet(user, message) ⇒
      tweetRepository.save(Tweet(user, message, hashtag))
    // TODO set lastTweetUsername on success!

    case Error(exception) ⇒
      log.error(s"Error occurred during listening: ${exception.getMessage}")
  }

}
