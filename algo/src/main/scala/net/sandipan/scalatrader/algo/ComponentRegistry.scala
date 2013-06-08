package net.sandipan.scalatrader.algo

import net.sandipan.scalatrader.zmq.{PublisherComponent, ContextComponent, SubscriberComponent}
import net.sandipan.scalatrader.common.model.{Trade, MarketData}
import net.sandipan.scalatrader.common.components.ConfigComponent
import com.typesafe.config.Config
import net.sandipan.scalatrader.common.util.{TimeServiceComponent, IdGeneratorComponent}

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

  override def algorithms =
    List(
      MACD.fromConfig(config),
      AlwaysBuy.fromConfig(config)
    ).flatten

  override def runner = new AlgorithmRunner

  override val idGenerator = new DefaultIdGenerator

  override val publisher = new Publisher

  override def timeService = new DefaultTimeService

  private def isAlgoEnabled(a: Algorithm) =
    config.getList("algorithm.enabled").contains(a.algorithmId)

}
