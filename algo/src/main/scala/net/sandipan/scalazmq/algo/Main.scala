package net.sandipan.scalazmq.algo

import org.jeromq.ZMQ
import com.typesafe.config.ConfigFactory

object Main {

  def main(args: Array[String]) {
    val context = ZMQ.context()
    val config = ConfigFactory.load().getConfig("algorithm")
    val registry = new ComponentRegistry

    // Can do this in two ways - have a single thread that grabs market data messages from the queue and sends them to all
    // algo threads, or have each algo thread have its own socket to the publisher.

    try {
      registry.startAllAlgorithms()
    } finally {
      registry.closeAllSockets()
    }

  }

}
