package io.github.bszwej.api

import akka.actor.ActorDSL.{Act, actor}
import akka.http.scaladsl.model.StatusCodes.{Created, NoContent, OK}
import io.github.bszwej.core.HashtagManagerActor._

class HashtagEndpointTest extends BaseApiTest {

  "HashtagEndpoint" should {

    "add hashtag" in {
      // given
      val hashtagActor = actor {
        new Act {
          become {
            case AddHashtag("tesla") ⇒ sender() ! HashtagCreated("tesla")
          }
        }
      }

      val route = new HashtagEndpoint(hashtagActor).route

      // when
      Post("/hashtags/tesla") ~> route ~> check {

        // then
        status mustBe Created
      }
    }

    "get a list of hashtags" in {
      // given
      val hashtagActor = actor {
        new Act {
          become {
            case GetHashtags ⇒ sender() ! Set("tesla", "apple", "poland")
          }
        }
      }

      val route = new HashtagEndpoint(hashtagActor).route

      // when
      Get("/hashtags") ~> route ~> check {

        // then
        status mustBe OK
        responseAs[List[String]] must contain allOf("tesla", "apple", "poland")
      }
    }

    "delete a hashtag" in {
      // given
      val hashtagActor = actor {
        new Act {
          become {
            case DeleteHashtag("tesla") ⇒ sender() ! HashtagDeleted("tesla")
          }
        }
      }

      val route = new HashtagEndpoint(hashtagActor).route

      // when
      Delete("/hashtags/tesla") ~> route ~> check {

        // then
        status mustBe NoContent
      }
    }
  }

}
