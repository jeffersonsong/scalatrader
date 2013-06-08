package net.sandipan.scalatrader.common.util

import org.slf4j.LoggerFactory

trait HasLogger {

  val log = LoggerFactory.getLogger(this.getClass)

}
