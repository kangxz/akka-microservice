package io.github.bszwej.api

import akka.http.scaladsl.server.Directives
import com.typesafe.scalalogging.LazyLogging
import de.heikoseeberger.akkahttpcirce.CirceSupport

trait BaseEndpoint
  extends Directives
  with CirceSupport
  with LazyLogging
  with EndpointTimeout
