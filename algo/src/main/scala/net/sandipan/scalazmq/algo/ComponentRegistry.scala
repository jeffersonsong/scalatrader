package net.sandipan.scalazmq.algo

import net.sandipan.scalazmq.zmq.{ContextComponent, SubscriberComponent}
import net.sandipan.scalazmq.common.model.MarketData
import net.sandipan.scalazmq.common.components.ConfigComponent
import com.typesafe.config.Config
import org.jeromq.ZMQ.Context

class ComponentRegistry(context: Context, config: Config)
  extends  AlgorithmRunnerComponent
  with SubscriberComponent[MarketData]
  with ContextComponent
  with ConfigComponent {

  override val subscription = new Subscription

  override val configProvider = ConfigProvider(config)

  override val contextProvider = ContextProvider(context)

  def algorithms = List(MACD.fromConfig(config))

  def runner = new AlgorithmRunner
}
