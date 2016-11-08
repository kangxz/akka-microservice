/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 */
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
