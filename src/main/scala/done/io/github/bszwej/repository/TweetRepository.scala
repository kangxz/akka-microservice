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
package done.io.github.bszwej.repository

import com.typesafe.scalalogging.LazyLogging
import done.io.github.bszwej.exception.{MongoException, MongoWriteException}
import done.io.github.bszwej.model.Tweet
import io.github.bszwej.MainConfig
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument

import scala.concurrent.{ExecutionContext, Future}

/**
  * Repository for tweets.
  */
trait TweetRepository {

  /**
    * Persists a tweet.
    *
    * @param tweet to be persisted
    */
  def save(tweet: Tweet): Future[RepositorySuccess.type]

}

case object RepositorySuccess

class MongoTweetRepository(collection: Future[BSONCollection])(implicit ec: ExecutionContext) extends TweetRepository with LazyLogging with MainConfig {

  override def save(tweet: Tweet): Future[RepositorySuccess.type] = {
    val document = BSONDocument(
      "username" → tweet.username,
      "message" → tweet.message,
      "hashtag" → tweet.hashtag
    )

    collection.flatMap(_.insert(document).map(_ ⇒ RepositorySuccess)) recoverWith {
      case WriteResult.Message(message) ⇒
        logger.error(s"Error during storing a tweet: $message.")
        Future.failed(new MongoWriteException(message))

      case e: Exception ⇒
        logger.error(s"Error during storing a tweet: ${e.getMessage}.")
        Future.failed(new MongoException(e.getMessage))
    }
  }
}
