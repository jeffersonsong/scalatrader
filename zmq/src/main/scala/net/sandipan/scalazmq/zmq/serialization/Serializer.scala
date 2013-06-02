package net.sandipan.scalazmq.zmq.serialization

import net.sandipan.scalazmq.common.model.{Signal, Trade, MarketData}
import net.sandipan.scalazmq.common.protos.Messages

trait Serializer[T] {

  def serialize(obj: T): Array[Byte]

}

object Serializer {

  implicit object marketDataSerializer extends Serializer[MarketData] {

    override def serialize(md: MarketData): Array[Byte] = {
      val proto = Messages.MarketData.newBuilder()
        .setId(md.id)
        .setAsk(md.ask.toString())
        .setBid(md.bid.toString())
        .setSymbol(md.symbol)
        .setVolume(md.volume)
        .setTimestamp(md.timestamp)
        .build()
      proto.toByteArray
    }
  }

  implicit object serializer extends Serializer[Trade] {

    def serialize(trade: Trade): Array[Byte] = {
      val message = Messages.Trade.newBuilder()
        .setId(trade.id)
        .setMarketDataId(trade.marketData.id)
        .setDirection(Signal.toProtoVal(trade.signal))
        .setTimestamp(trade.timestamp)
        .setAlgorithmId(trade.algorithmId)
        .build()
      message.toByteArray
    }
  }

}