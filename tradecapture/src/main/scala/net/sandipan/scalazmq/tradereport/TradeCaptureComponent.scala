package net.sandipan.scalazmq.tradereport

import net.sandipan.scalazmq.common.model.Trade
import net.sandipan.scalazmq.zmq.SubscriberComponent
import net.sandipan.scalazmq.common.util.HasLogger

trait TradeCaptureComponent {
  this: SubscriberComponent[Trade]
    with PersistenceComponent[Trade] =>

  def capturer: TradeCapturer

  class TradeCapturer extends HasLogger {

    def startCapturing() {
      log.info("Starting trade capture.")
      for (trade <- subscription.stream) {
        repository.persist(trade)
      }
    }

  }

}
