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
package done.io.github.bszwej.api

import akka.actor.ActorRef
import akka.http.scaladsl.model.StatusCodes
import akka.pattern.ask
import done.io.github.bszwej.HashtagManagerActor.AddHashtag

import scala.util.{Failure, Success}

class HashtagEndpoint(hashtagManagerActor: ActorRef) extends BaseEndpoint {

  val route = path("hashtags" / Segment) { hashtag ⇒
    pathEnd {
      post {
        onComplete((hashtagManagerActor ? AddHashtag(hashtag))) {
          case Success(_) ⇒
            complete("Hashtag added!")
          case Failure(e) ⇒
            logger.error(s"Error occurred: ${e.getMessage}")
            complete((StatusCodes.InternalServerError, "Error!"))
        }
      }
    }
  }
}
