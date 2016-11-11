/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 */
package done.io.github.bszwej

import akka.actor.{Actor, ActorLogging, Props}
import done.io.github.bszwej.TweetCollectorActor.{Error, NewTweet}
import done.io.github.bszwej.model.Tweet
import done.io.github.bszwej.repository.TweetRepository
import done.io.github.bszwej.twitter.ActorTweetListener
import twitter4j.{FilterQuery, TwitterStream}

object TweetCollectorActor {

  final case class NewTweet(author: String, message: String)

  final case class Error(exception: Exception)

  def props(hashtag: String, twitterStream: TwitterStream, tweetRepository: TweetRepository): Props =
    Props(new TweetCollectorActor(hashtag, twitterStream, tweetRepository))

}

class TweetCollectorActor(hashtag: String, twitterStreamClient: TwitterStream, tweetRepository: TweetRepository)
  extends Actor with ActorLogging with ActorTweetListener {

  override def preStart(): Unit = {
    twitterStreamClient.addListener(tweetListener(self))
    twitterStreamClient.filter(new FilterQuery().track(s"#$hashtag"))
  }

  override def receive: Receive = {
    case NewTweet(author, message) ⇒
      tweetRepository.save(Tweet(author, message, hashtag))

    case Error(exception) ⇒
      log.error(s"Error occurred during listening: ${exception.getMessage}")
  }
}
