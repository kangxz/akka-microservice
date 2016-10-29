package io.github.bszwej.api

import akka.http.scaladsl.server.Route
import io.github.bszwej.core.{Core, CoreActors}

import scala.concurrent.ExecutionContextExecutor

trait Api extends CoreActors with Core {

  implicit val ec: ExecutionContextExecutor = system.dispatcher

  lazy val routes: Route = new HashtagEndpoint(hashtagManagerActor).route

}
