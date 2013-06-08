package net.sandipan.scalatrader.zmq.serialization

import net.sandipan.scalatrader.common.model.{Trade, MarketData}
import net.sandipan.scalatrader.common.protos.Messages

trait Deserializer[T] {

  def deserialize(bytes: Array[Byte]): T

}


object Deserializer {

  implicit object marketDataDeserializer extends Deserializer[MarketData] {
    def deserialize(bytes: Array[Byte]): MarketData = {
      val msg = Messages.MarketData.parseFrom(bytes)
      MarketData.fromProto(msg)
    }
  }

  implicit object tradeDataDeserializer extends Deserializer[Trade] {

    def deserialize(bytes: Array[Byte]): Trade = {
      val msg = Messages.Trade.parseFrom(bytes)
      Trade.fromProto(msg)
    }
  }

}