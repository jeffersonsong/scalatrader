package net.sandipan.scalatrader.tradereport.mongo

import com.mongodb.casbah.MongoClient
import net.sandipan.scalatrader.common.components.ConfigComponent
import net.sandipan.scalatrader.common.util.HasLogger

trait MongoClientComponent {
  this: ConfigComponent =>

  def mongoClientProvider: MongoClientProvider

  class MongoClientProvider extends HasLogger {
    val mongoClient = {
      val host = config.getString("mongo.host")
      val port = config.getInt("mongo.port")
      log.info("Connecting to mongodb on %s:%s".format(host, port))
      MongoClient(host, port)
    }
  }

}
