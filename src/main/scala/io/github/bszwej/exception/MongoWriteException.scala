package io.github.bszwej.exception

/**
  * Represents write exception from MongoDB.
  *
  * @param message of an error
  */
class MongoWriteException(message: String) extends RuntimeException(message)
