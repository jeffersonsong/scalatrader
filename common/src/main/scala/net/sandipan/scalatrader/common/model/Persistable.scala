package net.sandipan.scalatrader.common.model

trait Persistable[T] {
  val entityName: String
}
