package net.sandipan.scalatrader.tradereport

import net.sandipan.scalatrader.common.model.Trade
import net.sandipan.scalatrader.zmq.SubscriberComponent
import net.sandipan.scalatrader.common.util.HasLogger

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
