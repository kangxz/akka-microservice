package io.github.bszwej

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging

object Microservice extends LazyLogging with Directives {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    // TODO Step 1:
    // - routing
    // - Http server
  }
}
