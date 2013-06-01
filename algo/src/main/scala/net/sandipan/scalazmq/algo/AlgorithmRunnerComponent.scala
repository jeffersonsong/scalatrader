package net.sandipan.scalazmq.algo

import net.sandipan.scalazmq.zmq.SubscriberComponent
import net.sandipan.scalazmq.common.model.MarketData
import net.sandipan.scalazmq.common.util.HasLogger

trait AlgorithmRunnerComponent {

  this: SubscriberComponent[MarketData] =>

  def algorithms: Seq[Algorithm]
  def runner: AlgorithmRunner

  class AlgorithmRunner extends HasLogger {

    def run() {
      // TODO parallelism
      for
      { d <- subscription.stream
        a <- algorithms } {
        a.submit(d) match {
          case Some(signal) => log.info("Generating signal: %s".format(signal))
          case None => // Do nothing
        }
      }
    }


  }

}
