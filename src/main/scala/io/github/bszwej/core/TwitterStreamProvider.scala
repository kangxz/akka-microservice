package io.github.bszwej.core

import io.github.bszwej.MainConfig
import twitter4j.auth.AccessToken
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{TwitterStream, TwitterStreamFactory}

trait TwitterStreamProvider extends MainConfig {

  val configuration =
    new ConfigurationBuilder()
      .setOAuthConsumerKey(config.getString("twitter.consumer.key"))
      .setOAuthConsumerSecret(config.getString("twitter.consumer.secret"))
      .setOAuthAccessToken(config.getString("twitter.token.accessToken"))
      .setOAuthAccessTokenSecret(config.getString("twitter.token.accessTokenSecret"))
      .build()

  private val twitterStreamFactory = new TwitterStreamFactory()
  private val token = new AccessToken(config.getString("twitter.token.accessToken"), config.getString("twitter.token.accessTokenSecret"))

  def twitterStream: TwitterStream = twitterStreamFactory.getInstance(token)

}
