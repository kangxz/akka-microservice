package io.github.bszwej.core

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import io.github.bszwej.MainConfig
import io.github.bszwej.api.Api

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

trait Core {

  protected implicit val system: ActorSystem

}

trait BootedCore extends Core with Api with MainConfig with LazyLogging {

  protected implicit lazy val system: ActorSystem = ActorSystem()
  protected implicit lazy val materializer = ActorMaterializer()

  implicit lazy val context: ExecutionContextExecutor = system.dispatcher

  private lazy val port = config.getInt("server.port")

  Http().bindAndHandle(routes, "0.0.0.0", port) onComplete {

    case Success(b) ⇒
      logger.info(s"Server started on port: $port")
      sys.addShutdownHook {
        b.unbind()
        system.terminate()
        logger.info("Application stopped.")
      }

    case Failure(e) ⇒
      logger.error("Server could not be started.")
      sys.addShutdownHook {
        system.terminate()
        logger.info("Application stopped.")
      }
  }
}
