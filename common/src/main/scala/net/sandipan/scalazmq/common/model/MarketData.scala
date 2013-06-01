package net.sandipan.scalazmq.common.model

import _root_.net.sandipan.scalazmq.common.protos.Messages
import net.sandipan.scalazmq.common.serialization.{Deserializer, Serializer}

/**
 * Simplified MarketData
 */
case class MarketData(symbol: String, bid: Double, ask: Double, volume: Int, timestamp: String)

object MarketData {

  implicit object marketDataSerializer extends Serializer[MarketData] {
    override def serialize(md: MarketData): Array[Byte] = {
      val proto = Messages.MarketData.newBuilder()
        .setAsk(md.ask)
        .setBid(md.bid)
        .setSymbol(md.symbol)
        .setVolume(md.volume)
        .setTimestamp(md.timestamp)
        .build()
      proto.toByteString().toByteArray()
    }
  }

  implicit object marketDataDeserializer extends Deserializer[MarketData] {
    def deserialize(bytes: Array[Byte]): MarketData = {
      val msg = Messages.MarketData.parseFrom(bytes)
      MarketData(msg.getSymbol(), msg.getBid(), msg.getAsk(), msg.getVolume(), msg.getTimestamp)
    }
  }

}