package io.github.bszwej.core

import akka.actor.{ActorRef, ActorRefFactory, Terminated}
import akka.testkit.TestProbe
import io.github.bszwej.core.HashtagManagerActor._

class HashtagManagerActorTest extends BaseActorTest {

  "HashtagManagerActor" can {

    "create children for each hashtag" in {
      // given
      val childMaker = stubFunction[String, ActorRefFactory, ActorRef]
      val hashtagManagerActor = system.actorOf(HashtagManagerActor.props(childMaker))

      // when
      hashtagManagerActor ! AddHashtag("tesla")

      // then
      expectMsg(HashtagCreated("tesla"))
      childMaker.verify("tesla", *)
    }

    "return hashtags" in {
      // given
      val childMaker = stubFunction[String, ActorRefFactory, ActorRef]
      val hashtagManagerActor = system.actorOf(HashtagManagerActor.props(childMaker))
      hashtagManagerActor ! AddHashtag("tesla")
      hashtagManagerActor ! AddHashtag("spacex")

      // when
      hashtagManagerActor ! GetHashtags

      // then
      expectMsg(HashtagCreated("tesla"))
      expectMsg(HashtagCreated("spacex"))
      expectMsgPF() {
        case set: Set[_] ⇒
          set should contain allOf("tesla", "spacex")
      }
    }

    "remove hashtags with killing children" in {
      // given
      val watcherProbe = TestProbe("watcher")
      val spacexProbe = TestProbe("spacex-actor")
      watcherProbe.watch(spacexProbe.ref)

      val childMaker = stubFunction[String, ActorRefFactory, ActorRef]

      childMaker.when(*, *).returns(spacexProbe.ref)

      val hashtagManagerActor = system.actorOf(HashtagManagerActor.props(childMaker))
      hashtagManagerActor ! AddHashtag("spacex")

      // when
      hashtagManagerActor ! DeleteHashtag("spacex")

      // then
      hashtagManagerActor ! GetHashtags

      expectMsg(HashtagCreated("spacex"))
      expectMsg(HashtagDeleted("spacex"))

      expectMsgPF() {
        case set: Set[_] ⇒
          set should be ('empty)
      }

      watcherProbe.expectMsgPF() {
        case Terminated(actor) ⇒
          actor shouldBe spacexProbe.ref
      }
    }
  }

}
