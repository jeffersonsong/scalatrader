package net.sandipan.scalazmq.tradereport.mongo

import com.mongodb.casbah.MongoClient
import net.sandipan.scalazmq.common.components.ConfigComponent

trait MongoClientComponent {
  this: ConfigComponent =>

  def mongoClient = mongoClientProvider.mongoClient

  def mongoClientProvider: MongoClientProvider

  class MongoClientProvider {
    lazy val mongoClient = {
      val host = config.getString("mongo.host")
      val port = config.getInt("mongo.port")
      MongoClient(host, port)
    }
  }


}
