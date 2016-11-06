package io.github.bszwej

import akka.actor.{ActorRefFactory, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import io.github.bszwej.api.HashtagEndpoint
import io.github.bszwej.core.mongo.MongoTweetCollectionProvider
import io.github.bszwej.core.repository.MongoTweetRepository
import io.github.bszwej.core.twitter.TwitterStreamProvider
import io.github.bszwej.core.{HashtagManagerActor, TweetCollectorActor}

import scala.concurrent.ExecutionContextExecutor

object Microservice extends LazyLogging with TwitterStreamProvider {

  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val context: ExecutionContextExecutor = system.dispatcher

    val tweetRepository = new MongoTweetRepository(MongoTweetCollectionProvider.collection)

    val tweetCollectorMaker =
      (hashtag: String, f: ActorRefFactory) ⇒
        f.actorOf(TweetCollectorActor.props(hashtag, twitterStream, tweetRepository), hashtag)

    val hashtagManagerActor = system.actorOf(HashtagManagerActor.props(tweetCollectorMaker), "HashtagManagerActor")

    val hashtagEndpointRoutes = new HashtagEndpoint(hashtagManagerActor).route

    val port = config.getInt("server.port")
    Http().bindAndHandle(hashtagEndpointRoutes, "0.0.0.0", port).onSuccess {
      case _ ⇒ logger.info(s"Server is up on port $port")
    }

  }
}
