package net.sandipan.scalatrader.tradereport

import com.typesafe.config.ConfigFactory

object Main extends App {
  val config = ConfigFactory.load()
  val registry = new ComponentRegistry(config)

  try {
    registry.capturer.startCapturing()
  } finally {
    registry.subscription.closeAllSockets()
  }
}
