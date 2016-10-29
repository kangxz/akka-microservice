package io.github.bszwej.core

import akka.testkit.{ImplicitSender, TestKit}
import io.github.bszwej.core.TestSystem.testSystem
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpecLike}

import scala.concurrent.duration.DurationInt

abstract class BaseActorTest
  extends TestKit(testSystem)
    with ImplicitSender
    with WordSpecLike
    with MockFactory
    with Matchers {

  val MsgDuration = 300 millis

}
