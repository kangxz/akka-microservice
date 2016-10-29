package io.github.bszwej.api

import akka.actor.ActorRef
import akka.http.scaladsl.model.StatusCodes
import akka.pattern.ask
import io.github.bszwej.core.HashtagManagerActor._

import scala.util.{Failure, Success}

class HashtagEndpoint(hashtagActor: ActorRef) extends BaseEndpoint {
  // TODO status endpoint with number or timestamp of last aggregation
  val route = pathPrefix("hashtags") {
    pathEnd {
      get {
        onComplete((hashtagActor ? GetHashtags).mapTo[Set[String]]) {
          case Success(tags) ⇒
            complete(tags)
          case Failure(e) ⇒
            complete(StatusCodes.InternalServerError → e.getMessage)
        }
      }
    } ~ path(Segment) { hashtag ⇒
      post {
        onComplete((hashtagActor ? AddHashtag(hashtag)).mapTo[HashtagCreated]) {
          case Success(r) ⇒
            complete(StatusCodes.Created → s"Hashtag '${r.hashtag}' created.")
          case Failure(e) ⇒
            complete(StatusCodes.InternalServerError → e.getMessage)
        }
      } ~ delete {
        onComplete((hashtagActor ? DeleteHashtag(hashtag)).mapTo[HashtagDeleted]) {
          case Success(r) ⇒
            complete(StatusCodes.NoContent → s"Hashtag '${r.hashtag}' deleted.")
          case Failure(e) ⇒
            complete(StatusCodes.InternalServerError → e.getMessage)
        }
      }
    }
  }
}
