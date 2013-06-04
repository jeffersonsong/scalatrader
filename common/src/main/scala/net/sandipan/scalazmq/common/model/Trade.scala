package net.sandipan.scalazmq.common.model

case class Trade(id: String, marketData: MarketData, signal: Signal, algorithmId: String, timestamp: String) extends Id[String]