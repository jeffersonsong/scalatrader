package net.sandipan.scalatrader.messagebroker

import com.typesafe.config.ConfigFactory
import org.jeromq.ZMQ

object Main extends App {

  val config = ConfigFactory.load()
  val registry = new ComponentRegistry(config)

  try {
    registry.brokerComponent.start()
  } finally {
    registry.brokerComponent.closeAllSockets()
  }

}