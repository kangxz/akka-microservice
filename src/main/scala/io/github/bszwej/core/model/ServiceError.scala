package io.github.bszwej.core.model

sealed abstract class ServiceError(message: String) {
  def getMessage: String = message
}

case class InternalServiceError(message: String) extends ServiceError(message)

case class ObjectNotFound(message: String) extends ServiceError(message)
