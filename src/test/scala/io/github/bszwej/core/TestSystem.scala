package io.github.bszwej.core

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

object TestSystem {

  val testSystem = {
    val config = ConfigFactory.parseString(
      """
        |akka.loggers = [akka.testkit.TestEventListener]
      """.stripMargin
    )
    ActorSystem("test-system", config)
  }

}
