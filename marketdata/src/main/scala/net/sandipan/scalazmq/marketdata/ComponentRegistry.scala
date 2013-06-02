package net.sandipan.scalazmq.marketdata

import com.typesafe.config.Config
import net.sandipan.scalazmq.zmq.{ContextComponent, PublisherComponent}
import net.sandipan.scalazmq.common.model.MarketData
import net.sandipan.scalazmq.common.components.ConfigComponent
import scala.util.Random

class ComponentRegistry(config: Config)
  extends MarketDataSenderComponent
  with PublisherComponent[MarketData]
  with ContextComponent
  with ConfigComponent {

  override lazy val configProvider = ConfigProvider(config.getConfig("marketData"))

  override lazy val contextProvider = ContextComponent.defaultContextProvider(config.getConfig("zmq"))

  override lazy val publisher = new Publisher[MarketData]

  override lazy val randomizationSeed = Random.nextLong()

  override lazy val marketDataSender = new MarketDataSender
}