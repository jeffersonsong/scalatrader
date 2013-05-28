package net.sandipan.scalazmq.messagebroker

import com.typesafe.config.ConfigFactory
import org.jeromq.ZMQ

object Main {

  def main(args: Array[String]) {

    val config = ConfigFactory.load().getConfig("messageBroker")
    val context = ZMQ.context()
    val registry = new ComponentRegistry(config, context)

    try {
      registry.brokerComponent.start()
    } finally {
      registry.brokerComponent.closeAllSockets()
    }

  }

}
