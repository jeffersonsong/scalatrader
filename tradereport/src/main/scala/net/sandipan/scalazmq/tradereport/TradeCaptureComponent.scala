package net.sandipan.scalazmq.tradereport

import net.sandipan.scalazmq.common.model.Trade
import net.sandipan.scalazmq.zmq.SubscriberComponent

trait TradeCaptureComponent {
  this: SubscriberComponent[Trade]
    with PersistenceComponent[Trade] =>

  def capturer: TradeCapturer

  class TradeCapturer {

    def startCapturing() {
      for (trade <- subscription.stream) {

      }
    }

  }

}
