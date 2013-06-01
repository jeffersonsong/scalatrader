package net.sandipan.scalazmq.common.model

sealed abstract trait Signal
case class BuySignal(symbol: String) extends Signal
case class SellSignal(symbol: String) extends Signal