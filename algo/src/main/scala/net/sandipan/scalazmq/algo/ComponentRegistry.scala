package net.sandipan.scalazmq.algo

import net.sandipan.scalazmq.zmq.{PublisherComponent, ContextComponent, SubscriberComponent}
import net.sandipan.scalazmq.common.model.{Trade, MarketData}
import net.sandipan.scalazmq.common.components.ConfigComponent
import com.typesafe.config.Config
import net.sandipan.scalazmq.common.util.{TimeServiceComponent, IdGeneratorComponent}

class ComponentRegistry(config: Config)
  extends  AlgorithmRunnerComponent
  with SubscriberComponent[MarketData]
  with ContextComponent
  with ConfigComponent
  with IdGeneratorComponent
  with PublisherComponent[Trade]
  with TimeServiceComponent {

  override val subscription = new Subscription

  override val configProvider = ConfigProvider(config)

  override val contextProvider = ContextComponent.defaultContextProvider(config)

  override def algorithms = List(MACD.fromConfig(config), new AlwaysBuy)

  override def runner = new AlgorithmRunner

  override val idGenerator = new DefaultIdGenerator

  override val publisher = new Publisher

  override def timeService = new DefaultTimeService
}
