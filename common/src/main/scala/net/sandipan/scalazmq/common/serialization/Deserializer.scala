package net.sandipan.scalazmq.common.serialization

trait Deserializer[T] {

  def deserialize(bytes: Array[Byte]): T

}
