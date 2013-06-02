package net.sandipan.scalazmq.marketdata

import com.typesafe.config.ConfigFactory

object Main {

  def main(arr: Array[String]) {

    val config = ConfigFactory.load()
    val registry = new ComponentRegistry(config)

    try {
      registry.marketDataSender.startSendingData()
    } finally {
      registry.publisher.closeSocket()
    }
  }

}
