package net.sandipan.scalatrader.algo

import org.scalatest.FunSpec
import net.sandipan.scalatrader.algo.MACD.Params
import net.sandipan.scalatrader.common.model.{BuySignal, Signal, SellSignal, MarketData}
import org.joda.time.LocalDateTime
import org.scalatest.matchers.ShouldMatchers
import java.util.UUID

class MACDSpec extends FunSpec with ShouldMatchers {

  describe("The MACD Algorithm") {

    val macd = new MACD(Params(12, 26, 9))

    it("should correctly generate signals") {
      val list1 = (1 to 40) map (BigDecimal(_))
      val list2 = ((1 to 39).reverse) map (BigDecimal(_))

      val marketData = (list1 ++ list2) map (x => MarketData(UUID.randomUUID().toString, "IBM", x, x, 1000, LocalDateTime.now().toString))

      var signals: List[Signal] = Nil
      var indices: List[Int] = Nil

      for (x <- marketData.indices) {
        val md = marketData(x)
        macd.submit(md) match {
          case Some(s) => {
            signals ::= s
            indices ::= x
          }
          case _ => // Do nothing
        }
      }

      signals should have size (2)

      indices(0) should equal (72)
      signals(0) should equal (BuySignal())

      signals(1) should equal (SellSignal())
      indices(1) should equal (40)

    }

  }


}
