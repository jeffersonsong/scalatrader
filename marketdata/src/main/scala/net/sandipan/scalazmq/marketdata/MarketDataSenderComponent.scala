package net.sandipan.scalazmq.marketdata

import net.sandipan.scalazmq.zmq.PublisherComponent
import net.sandipan.scalazmq.common.model.MarketData
import scala.collection.JavaConversions._
import net.sandipan.scalazmq.marketdata.rng.RNG
import net.sandipan.scalazmq.common.components.ConfigComponent
import org.joda.time.LocalDateTime

trait MarketDataSenderComponent {
  this: PublisherComponent[MarketData] with ConfigComponent =>

  def randomizationSeed: Long
  def marketDataSender: MarketDataSender

  class MarketDataSender {

    val sleepTime = config.getLong("sendInterval")

    val symbols = config.getStringList("symbols").toVector

    def randomMarketDataStream(rng: RNG = RNG.simple(randomizationSeed)): Stream[MarketData] = {
      val (bid, s1) = RNG.double(rng)
      val (ask, s2) = RNG.double(s1)
      val (vol, s3) = RNG.positiveInt(s2)
      val (symbolIndex, s4) = RNG.positiveInt(s3)
      val symbol = symbols(symbolIndex % symbols.size)
      val timestamp = LocalDateTime.now().toString()
      val marketData = MarketData(symbol, bid, ask, vol, timestamp)
      Stream.cons(marketData, randomMarketDataStream(s4))
    }

    def startSendingData() {
      for (md <- randomMarketDataStream()) {
        println("Publishing: %s".format(md))
        publisher.publish(md)
        Thread.sleep(sleepTime)
      }
    }

  }

}