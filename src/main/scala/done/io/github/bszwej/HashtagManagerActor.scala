package done.io.github.bszwej

import akka.actor.Actor
import done.io.github.bszwej.HashtagManagerActor.{AddHashtag, HashtagAdded}
import done.io.github.bszwej.repository.MongoTweetRepositoryProvider
import done.io.github.bszwej.twitter.TwitterStreamProvider

object HashtagManagerActor {

  case class AddHashtag(tagName: String)

  case class HashtagAdded(tagName: String)

}

class HashtagManagerActor
  extends Actor
    with TwitterStreamProvider
    with MongoTweetRepositoryProvider {

  override def receive: Receive = {

    case AddHashtag(tagName) ⇒
      context.actorOf(TweetCollectorActor.props(tagName, twitterStream, tweetRepository), tagName)
      sender() ! HashtagAdded(tagName)

  }
}
