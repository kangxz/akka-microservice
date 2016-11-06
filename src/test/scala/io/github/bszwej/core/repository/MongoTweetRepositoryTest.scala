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
package io.github.bszwej.core.repository

import io.github.bszwej.core.exception.MongoException
import io.github.bszwej.core.model.Tweet
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONString}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

class MongoTweetRepositoryTest extends BaseRepositoryTest {

  import scala.concurrent.ExecutionContext.Implicits.global

  "MongoTweetRepository" should {

    "store a tweet" in {
      //given
      val collectionName = s"${config.getString("mongo.collection-name")}-test"
      val collection = db.collection[BSONCollection](collectionName)
      val repository = new MongoTweetRepository(Future.successful(collection))

      //when
      repository.save(Tweet("user", "msg", "hashtag")).futureValue(PatienceTimeout)

      //then
      val query = BSONDocument("username" → "user", "message" → "msg", "hashtag" → "hashtag")
      val document = db
        .collection[BSONCollection](collectionName)
        .find(query).one[BSONDocument]
        .futureValue(PatienceTimeout).get

      document.get("username").get shouldBe BSONString("user")
    }

    "return MongoException if Mongo is down" in {
      //given
      val nonExistingConnection = Future.fromTry(driver.connection("mongodb://localhost:9919"))
      val db = nonExistingConnection.flatMap(_.database("nonexisting"))
      val collection = db.map(_.collection[BSONCollection]("nonexisting"))
      val repository = new MongoTweetRepository(collection)

      //when
      whenReady(repository.save(Tweet("user", "msg", "hashtag")).failed, Timeout(10 seconds)) { e ⇒

        // then
        e shouldBe a[MongoException]
      }
    }
  }

  override protected def afterEach(): Unit = db.drop()

}
