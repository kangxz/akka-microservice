package io.github.bszwej.api

import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpcirce.CirceSupport
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{MustMatchers, WordSpec}

class BaseApiTest
  extends WordSpec
    with ScalatestRouteTest
    with ScalaFutures
    with MustMatchers
    with CirceSupport
