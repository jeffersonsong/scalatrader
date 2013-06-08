package net.sandipan.scalatrader.tradereport.mongo

import com.mongodb.casbah.commons.MongoDBObject
import net.sandipan.scalatrader.common.model.{Signal, Trade}
import com.mongodb.DBObject


trait MongoConverter[T] {

  def convert(obj: T): DBObject

}

object MongoConverter {

  implicit object tradeConvertor extends MongoConverter[Trade] {
    def convert(obj: Trade) = MongoDBObject(
      "_id" -> obj.id,
      "algorithmId" -> obj.algorithmId,
      "marketDataId" -> obj.marketData.id,
      "side" -> Signal.toProtoVal(obj.signal).toString,
      "timestamp" -> obj.timestamp
    )
  }

}
