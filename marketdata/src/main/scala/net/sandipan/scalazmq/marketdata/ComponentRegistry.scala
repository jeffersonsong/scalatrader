package net.sandipan.scalazmq.marketdata

import com.typesafe.config.Config
import net.sandipan.scalazmq.zmq.{ContextComponent, PublisherComponent}
import net.sandipan.scalazmq.common.model.MarketData
import net.sandipan.scalazmq.common.components.ConfigComponent
import scala.util.Random
import net.sandipan.scalazmq.common.util.IdGeneratorComponent

class ComponentRegistry(config: Config)
  extends MarketDataSenderComponent
  with PublisherComponent[MarketData]
  with ContextComponent
  with ConfigComponent
  with IdGeneratorComponent {

  override lazy val configProvider = ConfigProvider(config)

  override lazy val contextProvider = ContextComponent.defaultContextProvider(config)

  override lazy val publisher = new Publisher

  override lazy val randomizationSeed = Random.nextLong()

  override lazy val marketDataSender = new MarketDataSender

  def idGenerator = new DefaultIdGenerator

}