package io.github.bszwej

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import io.github.bszwej.api.HashtagEndpoint

object Microservice extends LazyLogging {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    // TODO Final step:
    // - make HashtagManagerActor create TwitterCollectorActor

    val hashtagManagerActor = system.actorOf(Props[HashtagManagerActor], "HashtagManagerActor")

    val hashtagEndpoint = new HashtagEndpoint(hashtagManagerActor).route
    Http().bindAndHandle(hashtagEndpoint, "0.0.0.0", 8080)
  }
}
