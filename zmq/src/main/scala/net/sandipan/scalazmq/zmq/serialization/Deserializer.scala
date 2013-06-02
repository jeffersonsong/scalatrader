package net.sandipan.scalazmq.zmq.serialization

import net.sandipan.scalazmq.common.model.MarketData
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

}