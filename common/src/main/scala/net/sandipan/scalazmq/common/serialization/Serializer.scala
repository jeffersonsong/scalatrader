package net.sandipan.scalazmq.common.serialization

trait Serializer[T] {
  def serialize(obj: T): Array[Byte]
}
