package net.sandipan.scalazmq.zmq.serialization

import net.sandipan.scalazmq.common.model.{Signal, Trade, MarketData}
import net.sandipan.scalazmq.common.protos.Messages

trait Serializer[T] {

  def serialize(obj: T): Array[Byte]

}

object Serializer {

  implicit object marketDataSerializer extends Serializer[MarketData] {
    override def serialize(md: MarketData): Array[Byte] = md.toProto.toByteArray
  }

  implicit object tradeSerializer extends Serializer[Trade] {
    override def serialize(trade: Trade): Array[Byte] = trade.toProto.toByteArray
  }

}