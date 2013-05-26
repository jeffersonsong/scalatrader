package net.sandipan.scalazmq.common.services

import org.joda.time.{LocalDate, LocalDateTime}

/**
 * Time Service - to provide time deterministically (mainly for testing)
 */
trait TimeService {
  def currentTime: LocalDateTime
}

class TimeServiceDefaultImpl extends TimeService {
  override def currentTime: LocalDateTime = new LocalDateTime()
}
