package net.sandipan.scalatrader.common.util

import org.joda.time.{LocalDate, LocalDateTime}

trait TimeServiceComponent {

  def timeService: TimeService

  trait TimeService {
    def now(): String
  }

  class DefaultTimeService extends TimeService {
    def now(): String = new LocalDateTime().toString
  }

}
