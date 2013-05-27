package net.sandipan.scalazmq.marketdata

import com.typesafe.config.{ConfigFactory, Config}
import org.jeromq.ZMQ

object Main {

  val thisNode: String = "marketData" // TODO Left this way for testing, should be a System.getProperty or command-line arg.
  val config: Config = ConfigFactory.load().getConfig(thisNode)

  def main(arr: Array[String]) {
    val zmqContext = ZMQ.context()
    val registry = new ComponentRegistry(config, zmqContext)

    try {
      registry.marketDataSender.startSendingData()
    } finally {
      registry.publisher.closeSocket()
    }
  }

}
