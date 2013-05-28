package net.sandipan.scalazmq.marketdata

import com.typesafe.config.Config
import org.jeromq.ZMQ.Context
import net.sandipan.scalazmq.zmq.{ContextComponent, PublisherComponent}
import net.sandipan.scalazmq.common.model.MarketData
import net.sandipan.scalazmq.common.components.ConfigComponent
import scala.util.Random

class ComponentRegistry(config: Config, context: Context)
  extends MarketDataSenderComponent
  with PublisherComponent[MarketData]
  with ContextComponent
  with ConfigComponent {

  override lazy val configProvider = ConfigProvider(config)

  override lazy val contextProvider = ContextProvider(context)

  override lazy val publisher = new Publisher[MarketData]

  override lazy val randomizationSeed = Random.nextLong()

  override lazy val marketDataSender = new MarketDataSender
}