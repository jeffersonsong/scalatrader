package net.sandipan.scalazmq.messagebroker

import net.sandipan.scalazmq.zmq.{ContextComponent, MessageBrokerComponent}
import net.sandipan.scalazmq.common.components.ConfigComponent
import com.typesafe.config.Config
import org.jeromq.ZMQ.Context

class ComponentRegistry(config: Config, context: Context)
  extends MessageBrokerComponent
  with ContextComponent
  with ConfigComponent {

  override lazy val configProvider = new ConfigProvider(config)

  override lazy val contextProvider = new ContextProvider(context)

  override lazy val brokerComponent = new MessageBroker()

}