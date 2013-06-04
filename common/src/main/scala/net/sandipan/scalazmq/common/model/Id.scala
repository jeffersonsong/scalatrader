package net.sandipan.scalazmq.common.model

trait Id[T] {
  type Type = T
  def id: T
}
