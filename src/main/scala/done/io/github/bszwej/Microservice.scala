package done.io.github.bszwej

import akka.actor.{ActorRefFactory, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import done.io.github.bszwej.api.HashtagEndpoint
import done.io.github.bszwej.mongo.MongoTweetCollectionProvider
import done.io.github.bszwej.repository.MongoTweetRepository
import done.io.github.bszwej.twitter.TwitterStreamProvider

object Microservice extends LazyLogging with TwitterStreamProvider {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val hashtagManagerActor = system.actorOf(Props[HashtagManagerActor], "HashtagManagerActor")

    val hashtagEndpoint = new HashtagEndpoint(hashtagManagerActor).route
    Http().bindAndHandle(hashtagEndpoint, "0.0.0.0", 8080)
  }
}
