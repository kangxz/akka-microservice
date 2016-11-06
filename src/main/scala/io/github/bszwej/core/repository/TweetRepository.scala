package io.github.bszwej.core.repository

import com.typesafe.scalalogging.LazyLogging
import io.github.bszwej.MainConfig
import io.github.bszwej.core.exception.{MongoException, MongoWriteException}
import io.github.bszwej.core.model.Tweet
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
