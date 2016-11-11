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
package done.io.github.bszwej.twitter

import io.github.bszwej.MainConfig
import twitter4j.auth.AccessToken
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{TwitterStream, TwitterStreamFactory}

trait TwitterStreamProvider extends MainConfig {

  private val configuration =
    new ConfigurationBuilder()
      .setOAuthConsumerKey(config.getString("twitter.consumer.key"))
      .setOAuthConsumerSecret(config.getString("twitter.consumer.secret"))
      .setOAuthAccessToken(config.getString("twitter.token.accessToken"))
      .setOAuthAccessTokenSecret(config.getString("twitter.token.accessTokenSecret"))
      .build()

  private val twitterStreamFactory = new TwitterStreamFactory(configuration)
  private val token = new AccessToken(config.getString("twitter.token.accessToken"), config.getString("twitter.token.accessTokenSecret"))

  def twitterStream: TwitterStream = twitterStreamFactory.getInstance(token)

}
