package net.sandipan.scalazmq.algo

import net.sandipan.scalazmq.zmq.SubscriberComponent

trait AlgorithmRunnerComponent {

  this: SubscriberComponent[MarketData] =>

  def algorithms: Seq[Algorithm]
  def runner: AlgorithmRunner

  class AlgorithmRunner() {

    def run() {
      val stream = subscription.stream
    }

  }

}
