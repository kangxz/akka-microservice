package io.github.bszwej

import com.typesafe.config.ConfigFactory

/**
  * Trait for providing configuration.
  */
trait MainConfig {

  lazy val config = ConfigFactory.load()

}
