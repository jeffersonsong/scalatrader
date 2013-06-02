package net.sandipan.scalazmq.common.util

import java.util.UUID

trait IdGeneratorComponent {

  def idGenerator: IdGenerator

  trait IdGenerator {
    def generate(): String
  }

  class DefaultIdGenerator extends IdGenerator {
    override def generate(): String = UUID.randomUUID().toString
  }

}
