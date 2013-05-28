package net.sandipan.scalazmq.algo

import net.sandipan.scalazmq.common.model.{Trade, MarketData}

trait Algorithm {

  def provide(marketData: MarketData): Option[Trade]

}
