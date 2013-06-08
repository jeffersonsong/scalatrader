package net.sandipan.scalatrader.common.model

trait Id[T] {
  type Type = T
  def id: T
}
