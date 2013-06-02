package net.sandipan.scalazmq.messagebroker

import com.typesafe.config.ConfigFactory
import org.jeromq.ZMQ

object Main {

  def main(args: Array[String]) {

    val config = ConfigFactory.load()
    val registry = new ComponentRegistry(config)

    try {
      registry.brokerComponent.start()
    } finally {
      registry.brokerComponent.closeAllSockets()
    }

  }

}
