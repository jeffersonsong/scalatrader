package net.sandipan.scalazmq.algo

import org.jeromq.ZMQ
import com.typesafe.config.ConfigFactory

object Main {

  def main(args: Array[String]) {
    val context = ZMQ.context()
    val config = ConfigFactory.load().getConfig("algorithm")
    val registry = new ComponentRegistry(context, config)

    try {
      registry.runner.run()
    } finally {
      registry.subscription.closeSocket()
    }

  }

}
