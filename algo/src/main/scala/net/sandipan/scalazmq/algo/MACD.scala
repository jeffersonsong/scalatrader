package net.sandipan.scalazmq.algo

import net.sandipan.scalazmq.common.model.{SellSignal, BuySignal, Signal, MarketData}
import com.typesafe.config.Config

class MACD(config: Config) extends Algorithm {

  import MACD._

  private val params: Params = {
    val macdParams = config.getConfig("macdParameters")
    val slow = macdParams.getInt("slow")
    val fast = macdParams.getInt("fast")
    val signal = macdParams.getInt("signal")
    (slow, fast, signal)
  }

  private var dataMap: Map[Symbol, SymbolData] = Map()

  def submit(marketData: MarketData): Option[Signal] = {
    val symbol = marketData.symbol
    pushData(symbol)
    checkForCrossover(symbol)
  }

  def pushData(symbol: Symbol) {
    val symbolData = dataMap(symbol)
    // TODO
  }

  def checkForCrossover(symbol: String): Option[Signal] = {
    val symbolData = dataMap(symbol)

    def isBuySignal(latest: BigDecimal, previous: BigDecimal): Boolean =
      (latest < 0 && previous >= 0)

    def isSellSignal(latest: BigDecimal, previous: BigDecimal): Boolean =
      (latest > 0 && previous <= 0)

    for {head <- symbolData.headOption
         prev <- symbolData.tail.headOption
         latestSignal <- head.signalValue
         prevSignal <- prev.signalValue
           if (isBuySignal(latestSignal, prevSignal)
             || isSellSignal(latestSignal, prevSignal)) } yield {

      if (isBuySignal(latestSignal, prevSignal))
        BuySignal(symbol)
      else
        SellSignal(symbol)
    }
  }

}

object MACD {

  type Slow = Int
  type Fast = Int
  type Sig = Int
  type Params = (Slow, Fast, Sig)
  type Symbol = String
  type Money = BigDecimal
  type Price = Money

  type SymbolData = Seq[DataPoint]
  case class DataPoint(price: Money, fastSma: Option[Money], fastEma: Option[Money],
                       slowSMA: Option[Money], slowEMA: Option[Money], difference: Option[Money], signalValue: Option[BigDecimal])

}
