package net.sandipan.scalazmq.algo

import net.sandipan.scalazmq.common.model.{BuySignal, Signal, MarketData}

/**
 * I will not accept responsibility for any losses if you
 * decide to use this algorithm in your own trading system...
 */
class AlwaysBuy extends Algorithm {

  override val algorithmId: String = "AlwaysBuy"

  override def submit(marketData: MarketData): Option[Signal] = Some(BuySignal())

}
