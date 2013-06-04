package net.sandipan.scalazmq.zmq.serialization

import net.sandipan.scalazmq.common.model.{Trade, MarketData}
import net.sandipan.scalazmq.common.protos.Messages

trait Deserializer[T] {

  def deserialize(bytes: Array[Byte]): T

}


object Deserializer {

  implicit object marketDataDeserializer extends Deserializer[MarketData] {
    def deserialize(bytes: Array[Byte]): MarketData = {
      val msg = Messages.MarketData.parseFrom(bytes)
      MarketData(msg.getId, msg.getSymbol, BigDecimal(msg.getBid), BigDecimal(msg.getAsk), msg.getVolume, msg.getTimestamp)
    }
  }

  implicit object tradeDataDeserializer extends Deserializer[Trade] {

    def deserialize(bytes: Array[Byte]): Trade = {
      // TODO... Slight complication getting the MarketData back because we only serialized the id!
      val msg = Messages.Trade.parseFrom(bytes)
      Trade(msg.getId, null, msg.getDirection, )
    }
  }

}