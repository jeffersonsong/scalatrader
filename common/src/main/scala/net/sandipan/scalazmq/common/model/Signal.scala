package net.sandipan.scalazmq.common.model

import net.sandipan.scalazmq.common.protos.Messages

sealed trait Signal
case class BuySignal(symbol: String) extends Signal
case class SellSignal(symbol: String) extends Signal

object Signal {
  def toProtoVal(s: Signal): Messages.Trade.Direction = s match {
    case _: BuySignal => Messages.Trade.Direction.BUY
    case _: SellSignal => Messages.Trade.Direction.SELL
  }
}