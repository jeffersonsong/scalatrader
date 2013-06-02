package net.sandipan.scalazmq.algo

import com.typesafe.config.ConfigFactory

object Main {

  def main(args: Array[String]) {
    val config = ConfigFactory.load()
    val registry = new ComponentRegistry(config)

    try {
      registry.runner.run()
    } finally {
      registry.subscription.closeSocket()
    }

  }

}
