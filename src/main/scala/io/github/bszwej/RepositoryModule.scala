package io.github.bszwej

import io.github.bszwej.core.repository.MongoTweetRepository

trait RepositoryModule {

  val tweetRepository = new MongoTweetRepository

}
