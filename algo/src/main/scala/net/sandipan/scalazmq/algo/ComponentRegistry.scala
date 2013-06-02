package net.sandipan.scalazmq.algo

import net.sandipan.scalazmq.zmq.{ContextComponent, SubscriberComponent}
import net.sandipan.scalazmq.common.model.MarketData
import net.sandipan.scalazmq.common.components.ConfigComponent
import com.typesafe.config.Config

class ComponentRegistry(config: Config)
  extends  AlgorithmRunnerComponent
  with SubscriberComponent[MarketData]
  with ContextComponent
  with ConfigComponent {

  private val algoConfig = config.getConfig("algorithm")

  override val subscription = new Subscription

  override val configProvider = ConfigProvider(algoConfig)

  override val contextProvider = ContextComponent.defaultContextProvider(config.getConfig("zmq"))

  def algorithms = List(MACD.fromConfig(algoConfig), new AlwaysBuy)

  def runner = new AlgorithmRunner
}
