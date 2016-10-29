package io.github.bszwej.core.mongo

import com.mongodb.async.client.MongoClients
import io.github.bszwej.MainConfig

object MongoDatabaseProvider extends MainConfig {

  val database = MongoClients.create().getDatabase(config.getString("mongo.database-name"))

}
