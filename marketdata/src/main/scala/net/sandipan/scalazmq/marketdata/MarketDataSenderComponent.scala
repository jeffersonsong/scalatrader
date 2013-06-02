package net.sandipan.scalazmq.marketdata

import net.sandipan.scalazmq.zmq.PublisherComponent
import net.sandipan.scalazmq.common.model.MarketData
import scala.collection.JavaConversions._
import net.sandipan.scalazmq.marketdata.rng.RNG
import net.sandipan.scalazmq.common.components.ConfigComponent
import org.joda.time.LocalDateTime
import net.sandipan.scalazmq.common.util.{IdGeneratorComponent, HasLogger}

trait MarketDataSenderComponent {
  this: PublisherComponent[MarketData]
    with ConfigComponent
    with IdGeneratorComponent =>

  def randomizationSeed: Long
  def marketDataSender: MarketDataSender

  class MarketDataSender extends HasLogger {

    val sleepTime = config.getLong("marketData.sendInterval")

    val symbols = config.getStringList("marketData.symbols").toVector

    def randomMarketDataStream(rng: RNG = RNG.simple(randomizationSeed)): Stream[MarketData] = {
      val (bid, s1) = RNG.double(rng)
      val (ask, s2) = RNG.double(s1)
      val (vol, s3) = RNG.positiveInt(s2)
      val (symbolIndex, s4) = RNG.positiveInt(s3)
      val symbol = symbols(symbolIndex % symbols.size)
      val timestamp = LocalDateTime.now().toString
      val marketData = MarketData(idGenerator.generate(), symbol, bid, ask, vol, timestamp)
      Stream.cons(marketData, randomMarketDataStream(s4))
    }

    def startSendingData() {
      for (md <- randomMarketDataStream()) {
        if (log.isDebugEnabled)
          log.debug("Publishing: %s".format(md))
        publisher.publish(md)
        if (sleepTime > 0)
          Thread.sleep(sleepTime)
      }
    }

  }

}