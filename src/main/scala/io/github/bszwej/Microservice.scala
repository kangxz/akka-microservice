package io.github.bszwej

import io.github.bszwej.api.Api
import io.github.bszwej.core.{BootedCore, Core, CoreActors}

object Microservice extends App with BootedCore with Core with CoreActors with Api
