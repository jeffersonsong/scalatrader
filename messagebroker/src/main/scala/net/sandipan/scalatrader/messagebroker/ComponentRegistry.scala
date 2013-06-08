package net.sandipan.scalatrader.messagebroker

import net.sandipan.scalatrader.zmq.{ContextComponent, MessageBrokerComponent}
import net.sandipan.scalatrader.common.components.ConfigComponent
import com.typesafe.config.Config
import org.jeromq.ZMQ.Context

class ComponentRegistry(config: Config)
  extends MessageBrokerComponent
  with ContextComponent
  with ConfigComponent {

  override lazy val configProvider = new ConfigProvider(config)

  override lazy val contextProvider = ContextComponent.defaultContextProvider(config)

  override lazy val brokerComponent = new MessageBroker()

}