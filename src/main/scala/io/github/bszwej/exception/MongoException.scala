package io.github.bszwej.exception

/**
  * Represents exceptions from MongoDB.
  *
  * @param message of an error
  */
class MongoException(message: String) extends RuntimeException(message)
