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

import io.github.bszwej.MainConfig
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver}

import scala.concurrent.duration.DurationInt

abstract class BaseRepositoryTest
  extends WordSpec
    with MainConfig
    with ScalaFutures
    with Matchers
    with BeforeAndAfterEach {

  import scala.concurrent.ExecutionContext.Implicits.global

  val PatienceTimeout = Timeout(1 second)

  private val connectionUri = "mongodb://localhost:27017"
  private val dbName = s"${config.getString("mongo.database-name")}-test"

  val driver: MongoDriver = MongoDriver()
  val connection: MongoConnection = driver.connection(connectionUri).get
  val db: DefaultDB = connection.database(dbName).futureValue(PatienceTimeout)

}
