package io.github.bszwej

import akka.actor.Actor
import io.github.bszwej.HashtagManagerActor.{AddHashtag, HashtagAdded}
import io.github.bszwej.repository.MongoTweetRepositoryProvider
import io.github.bszwej.twitter.TwitterStreamProvider

object HashtagManagerActor {

  case class AddHashtag(tagName: String)

  case class HashtagAdded(tagName: String)

}

class HashtagManagerActor extends Actor with TwitterStreamProvider with MongoTweetRepositoryProvider {

  override def receive: Receive = {

    case AddHashtag(tagName) ⇒
      sender() ! HashtagAdded(tagName)

  }
}
