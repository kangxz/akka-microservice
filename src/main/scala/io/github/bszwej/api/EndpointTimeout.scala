package io.github.bszwej.api

import akka.util.Timeout

import scala.concurrent.duration.DurationInt

trait EndpointTimeout {

  implicit val timeout: Timeout = Timeout(3 seconds)

}
