package net.sandipan.scalazmq.tradereport.mongo

import com.mongodb.casbah.commons.MongoDBObject
import net.sandipan.scalazmq.common.model.{Signal, Trade}


trait MongoConverter[T] {

  def convert(obj: T): MongoDBObject

}

object MongoConverter {

  implicit object tradeConvertor extends MongoConverter[Trade] {
    def convert(obj: Trade): MongoDBObject = MongoDBObject(
      "id" -> obj.id,
      "algorithmId" -> obj.algorithmId,
      "marketDataId" -> obj.marketData.id,
      "side" -> Signal.toProtoVal(obj.signal).toString,
      "timestamp" -> obj.timestamp
    )
  }

}
