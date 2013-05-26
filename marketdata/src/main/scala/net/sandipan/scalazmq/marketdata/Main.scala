package net.sandipan.scalazmq.marketdata

import com.typesafe.config.{ConfigFactory, Config}
import org.jeromq.ZMQ

object Main {

  val config = ConfigFactory.load("marketdata")

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
