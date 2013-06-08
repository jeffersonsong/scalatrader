package net.sandipan.scalatrader.common.model

import net.sandipan.scalatrader.common.protos.Messages


/**
 * Simplified MarketData
 */
case class MarketData(id: String, symbol: String, bid: BigDecimal, ask: BigDecimal, volume: Int, timestamp: String) extends Id[String] {

  def toProto = Messages.MarketData.newBuilder()
    .setId(id)
    .setSymbol(symbol)
    .setBid(bid.toString())
    .setAsk(ask.toString())
    .setVolume(volume)
    .setTimestamp(timestamp)
    .build()

}

object MarketData {

  def fromProto(msg: Messages.MarketData) =
    MarketData(msg.getId, msg.getSymbol, BigDecimal(msg.getBid), BigDecimal(msg.getAsk), msg.getVolume, msg.getTimestamp)

}
