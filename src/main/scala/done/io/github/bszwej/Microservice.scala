package done.io.github.bszwej

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging

object Microservice extends LazyLogging with Directives {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val route = path("hashtags" / Segment) { hashtag ⇒
      pathEnd {
        post {
          complete("Hashtag added!")
        }
      }
    }

    Http().bindAndHandle(route, "0.0.0.0", 8080)
  }
}
