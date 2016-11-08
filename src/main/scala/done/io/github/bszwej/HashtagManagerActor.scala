package done.io.github.bszwej

import akka.actor.Actor
import done.io.github.bszwej.HashtagManagerActor.{AddHashtag, HashtagAdded}

object HashtagManagerActor {

  final case class AddHashtag(hashtag: String)

  final case object HashtagAdded

}

class HashtagManagerActor extends Actor with MainConfig {

  override def receive: Receive = {

    case AddHashtag(hashtag) ⇒
      sender() ! HashtagAdded

  }
}
