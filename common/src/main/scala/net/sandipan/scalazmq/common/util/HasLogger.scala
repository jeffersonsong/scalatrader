package net.sandipan.scalazmq.common.util

import org.slf4j.LoggerFactory

trait HasLogger {

  val log = LoggerFactory.getLogger(this.getClass)

}
