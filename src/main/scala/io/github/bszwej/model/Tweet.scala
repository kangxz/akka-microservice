package io.github.bszwej.model

/**
  * Case class representing a tweet.
  *
  * @param username of a tweet's author
  * @param message  of a tweet
  * @param hashtag  contained in a tweet
  */
final case class Tweet(username: String, message: String, hashtag: String)
