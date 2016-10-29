package io.github.bszwej.core.repository

import com.typesafe.scalalogging.LazyLogging
import io.github.bszwej.core.model.Tweet

import scala.concurrent.Future

/**
  * Repository for tweets.
  */
trait TweetRepository {

  /**
    * Persists a tweet.
    *
    * @param tweet to be persisted
    */
  def save(tweet: Tweet): Future[String]

}

class MongoTweetRepository extends TweetRepository with LazyLogging {

  override def save(tweet: Tweet): Future[String] = {
    logger.info(s"Persisting tweet '$tweet' in MongoDB.")
    Future.successful("hi")
  }

}
