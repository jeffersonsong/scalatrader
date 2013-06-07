package net.sandipan.scalazmq.algo

import net.sandipan.scalazmq.common.model.{Signal, MarketData}
import com.typesafe.config.Config

trait Algorithm {

  def submit(marketData: MarketData): Option[Signal]

  def algorithmId: String

}

object Algorithm {

  def algorithmEnabled(config: Config, id: String) =
    config.getStringList("algorithm.enabled").contains(id)

  def fromConfig[T <: Algorithm](config: Config, id: String)(fn: Config => T): Option[T] = {
    if (algorithmEnabled(config, id))
      Some(fn(config.getConfig("algorithm." + id)))
    else
      None
  }

}
