package io.github.bszwej

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import io.github.bszwej.api.HashtagEndpoint

object Microservice extends LazyLogging {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    // TODO Step 2:
    // - HashtagManagerActor
    // - integrate it with endpoint

    val hashtagEndpoint = new HashtagEndpoint().route
    Http().bindAndHandle(hashtagEndpoint, "0.0.0.0", 8080)
  }
}
