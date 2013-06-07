package net.sandipan.scalazmq.algo

import net.sandipan.scalazmq.common.model.{BuySignal, Signal, MarketData}
import com.typesafe.config.Config

/**
 * I will not accept responsibility for any losses if you
 * decide to use this algorithm in your own trading system...
 */
class AlwaysBuy extends Algorithm {

  override val algorithmId: String = AlwaysBuy.ALGORITHM_ID

  override def submit(marketData: MarketData): Option[Signal] = Some(BuySignal())

}

object AlwaysBuy {

  val ALGORITHM_ID = "AlwaysBuy"

  def fromConfig(config: Config) =
    Algorithm.fromConfig(config, ALGORITHM_ID)(_ => new AlwaysBuy)

}