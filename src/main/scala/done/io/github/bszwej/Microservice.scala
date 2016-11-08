package done.io.github.bszwej

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import done.io.github.bszwej.api.HashtagEndpoint

object Microservice extends LazyLogging with Directives {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val hashtagManagerActor = system.actorOf(Props[HashtagManagerActor], "HashtagManagerActor")

    val hashtagEndpoint = new HashtagEndpoint(hashtagManagerActor).route
    Http().bindAndHandle(hashtagEndpoint, "0.0.0.0", 8080)
  }
}
