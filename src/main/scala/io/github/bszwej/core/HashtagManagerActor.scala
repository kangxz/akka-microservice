package io.github.bszwej.core

import akka.actor.{Actor, ActorRef, ActorRefFactory, Props}
import io.github.bszwej.MainConfig
import io.github.bszwej.core.HashtagManagerActor._
import io.github.bszwej.core.twitter.TwitterStreamProvider

object HashtagManagerActor {

  final case class AddHashtag(hashtag: String)

  final case object GetHashtags

  final case class DeleteHashtag(hashtag: String)

  final case object HashtagAdded

  final case class Hashtags(hashtags: Set[String])

  final case class HashtagCreated(hashtag: String)

  final case class HashtagDeleted(hashtag: String)

  def props(childMaker: (String, ActorRefFactory) ⇒ ActorRef): Props =
    Props(new HashtagManagerActor(childMaker))
}

class HashtagManagerActor(childMaker: (String, ActorRefFactory) ⇒ ActorRef)
  extends Actor with MainConfig with TwitterStreamProvider {

  var childRegistry: Map[String, ActorRef] = Map()

  override def receive: Receive = {

    case AddHashtag(hashtag) ⇒
      // Create child
      val child = childMaker(hashtag, context)
      // Add it to the registry
      childRegistry = childRegistry + (hashtag → child)
      // Reply with HashtagCreated(hashtag)
      sender() ! HashtagCreated(hashtag)

    case GetHashtags ⇒
      sender() ! childRegistry.keySet

    case DeleteHashtag(hashtag) ⇒
      context.stop(childRegistry(hashtag))
      childRegistry = childRegistry - hashtag
      sender() ! HashtagDeleted(hashtag)
  }
}
