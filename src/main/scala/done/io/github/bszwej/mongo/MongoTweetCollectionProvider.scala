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
package done.io.github.bszwej.mongo

import com.typesafe.scalalogging.LazyLogging
import io.github.bszwej.MainConfig
import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.bson.BSONCollection

import scala.concurrent.Future
import scala.util.{Failure, Success}

object MongoTweetCollectionProvider extends MainConfig with LazyLogging {

  import scala.concurrent.ExecutionContext.Implicits.global

  private val driver: MongoDriver = MongoDriver()
  private val connection = Future.fromTry(driver.connection("mongodb://localhost:27017"))

  private val database = connection.flatMap(_.database(config.getString("mongo.database-name")))
  val collection: Future[BSONCollection] = database.map(_.collection[BSONCollection](config.getString("mongo.collection-name")))

  database.onComplete {
    case Success(db) ⇒
      logger.info(s"Successfully connected to the database '${db.name}'.")

    case Failure(e) ⇒
      logger.error(s"Error connection to the database: ${e.getMessage}.")
  }
}
