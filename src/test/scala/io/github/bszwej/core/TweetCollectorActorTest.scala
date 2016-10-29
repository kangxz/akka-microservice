package io.github.bszwej.core

import akka.testkit.EventFilter
import io.github.bszwej.core.TweetCollectorActor.{Error, NewTweet}
import io.github.bszwej.core.model.Tweet
import io.github.bszwej.core.repository.TweetRepository
import org.scalatest.OneInstancePerTest
import twitter4j.{FilterQuery, TwitterStream}

class TweetCollectorActorTest extends BaseActorTest with OneInstancePerTest{

  val twitterStream = stub[TwitterStream]
  val tweetRepository = stub[TweetRepository]

  "HashtagCollectorActor" should {

    "save a new tweet" in {
      // given
      val hashtagCollectorActor = system.actorOf(TweetCollectorActor.props("tesla", twitterStream, tweetRepository))

      // when
      hashtagCollectorActor ! NewTweet("bartek", "Tesla is Awesome!")
      hashtagCollectorActor ! NewTweet("other", "SpaceX and Tesla are Awesome!")

      // then
      expectNoMsg(MsgDuration)
      (twitterStream.addListener _).verify(*).once()
      (twitterStream.filter(_: FilterQuery)).verify(new FilterQuery().track("#tesla")).once()
      (tweetRepository.save _).verify(Tweet("bartek", "Tesla is Awesome!", "tesla"))
      (tweetRepository.save _).verify(Tweet("other", "SpaceX and Tesla are Awesome!", "tesla"))
    }

    "log failure" in {
      // given
      val hashtagCollectorActor = system.actorOf(TweetCollectorActor.props("tesla", twitterStream, tweetRepository))

      // when
      // then
      val eventFilter = EventFilter.error(
        start = "Error occurred during listening",
        occurrences = 1,
        source = hashtagCollectorActor.path.toString)

      eventFilter.intercept {
        hashtagCollectorActor ! Error(new Exception("twitter error..."))
      }
    }
  }
}
