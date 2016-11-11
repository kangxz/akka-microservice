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

import done.io.github.bszwej.mongo.MongoTweetCollectionProvider

trait MongoTweetRepositoryProvider {

  import scala.concurrent.ExecutionContext.Implicits.global

  val tweetRepository = new MongoTweetRepository(MongoTweetCollectionProvider.collection)

}
