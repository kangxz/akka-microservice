package done.io.github.bszwej.api

import akka.actor.ActorRef
import akka.pattern.ask
import done.io.github.bszwej.HashtagManagerActor.{AddHashtag, HashtagAdded}

import scala.util.{Failure, Success}

class HashtagEndpoint(hashtagManagerActor: ActorRef) extends BaseEndpoint {

  val route = path("hashtags" / Segment) { hashtag ⇒
    pathEnd {
      post {
        onComplete((hashtagManagerActor ? AddHashtag(hashtag)).mapTo[HashtagAdded]) {
          case Success(ha) ⇒
            complete(s"Hashtag '${ha.tagName}' added!")
          case Failure(e) ⇒
            logger.error(s"Error occurred: ${e.getMessage}")
            complete(s"Failure when adding hashtag: ${e.getMessage}")
        }
      }
    }
  }
}
