package done.io.github.bszwej

import akka.actor.Actor
import done.io.github.bszwej.HashtagManagerActor.{AddHashtag, HashtagAdded}

object HashtagManagerActor {

  case class AddHashtag(tagName: String)

  case class HashtagAdded(tagName: String)

}

class HashtagManagerActor extends Actor with MainConfig {

  override def receive: Receive = {

    case AddHashtag(tagName) ⇒
      sender() ! HashtagAdded(tagName)

  }
}
