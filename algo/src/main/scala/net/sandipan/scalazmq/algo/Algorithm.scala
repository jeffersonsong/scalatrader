package net.sandipan.scalazmq.algo

import net.sandipan.scalazmq.common.model.{Signal, MarketData}

trait Algorithm {

  def submit(marketData: MarketData): Option[Signal]

  def algorithmId: String

}
