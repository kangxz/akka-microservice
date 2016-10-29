package io.github.bszwej.api

import akka.http.scaladsl.marshalling.{Marshaller, ToResponseMarshaller}
import akka.http.scaladsl.model.StatusCodes.{InternalServerError, NotFound}
import akka.http.scaladsl.model._
import io.github.bszwej.core.model.{InternalServiceError, ObjectNotFound, ServiceError}

trait JsonSupport {

  implicit val serviceErrorMarshaller: ToResponseMarshaller[ServiceError] = Marshaller.combined {

    case InternalServiceError(message) ⇒
      errorResponse(InternalServerError, message)

    case ObjectNotFound(message) ⇒
      errorResponse(NotFound, message)
  }

  private def errorResponse(status: StatusCode, message: String) =
    HttpResponse(status = status, entity = HttpEntity(ContentTypes.`application/json`, s"""{"message": "$message"}"""))
}
