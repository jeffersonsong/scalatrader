package net.sandipan.scalazmq.common.model


/**
 * Simplified MarketData
 */
case class MarketData(id: String, symbol: String, bid: BigDecimal, ask: BigDecimal, volume: Int, timestamp: String)
