package net.sandipan.scalazmq.common.model

import net.sandipan.scalazmq.common.protos.Messages

case class Trade(id: String, marketData: MarketData, signal: Signal, algorithmId: String, timestamp: String) extends Id[String] {

  def toProto = Messages.Trade.newBuilder()
    .setId(id)
    .setMarketData(marketData.toProto)
    .setDirection(Signal.toProtoVal(signal))
    .setAlgorithmId(algorithmId)
    .setTimestamp(timestamp)
    .build()

}

object Trade {

  def fromProto(msg: Messages.Trade) =
    Trade(msg.getId(),
      MarketData.fromProto(msg.getMarketData),
      Signal.fromProtoVal(msg.getDirection),
      msg.getAlgorithmId,
      msg.getTimestamp)

  implicit object persistableKey extends Persistable[Trade] {
    val entityName: String = "trades"
  }

}