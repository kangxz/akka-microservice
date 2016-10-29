package io.github.bszwej.core

import akka.actor.ActorRefFactory
import io.github.bszwej.RepositoryModule

trait CoreActors extends RepositoryModule with TwitterStreamProvider {
  this: Core ⇒

  val tweetCollectorMaker =
    (hashtag: String, f: ActorRefFactory) ⇒
      f.actorOf(TweetCollectorActor.props(hashtag, twitterStream, tweetRepository), hashtag)

  val hashtagManagerActor = system.actorOf(HashtagManagerActor.props(tweetCollectorMaker), "HashtagManagerActor")

}
