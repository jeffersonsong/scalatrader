package net.sandipan.scalazmq.common.model

sealed trait Signal
case class BuySignal(symbol: String) extends Signal
case class SellSignal(symbol: String) extends Signal
case class NoopSignal() extends Signal