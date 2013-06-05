package net.sandipan.scalazmq.common.model

trait Persistable[T] {
  val entityName: String
}
