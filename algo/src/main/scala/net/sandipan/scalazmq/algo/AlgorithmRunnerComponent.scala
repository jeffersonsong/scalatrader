package net.sandipan.scalazmq.algo

import net.sandipan.scalazmq.zmq.{PublisherComponent, SubscriberComponent}
import net.sandipan.scalazmq.common.model.{Trade, MarketData}
import net.sandipan.scalazmq.common.util.{TimeServiceComponent, IdGeneratorComponent, HasLogger}

trait AlgorithmRunnerComponent {

  this: SubscriberComponent[MarketData]
    with IdGeneratorComponent
    with TimeServiceComponent
    with PublisherComponent[Trade] =>

  def algorithms: Seq[Algorithm]

  def runner: AlgorithmRunner

  class AlgorithmRunner extends HasLogger {

    def run() {
      // TODO parallelism / concurrency.
      for {data <- subscription.stream
           algo <- algorithms} {
        log.debug("Processing: %s".format(data)) // TODO Remove this line
        algo.submit(data) match {
          case Some(signal) => {
            log.debug("Generating signal: %s".format(signal))
            val t = Trade(idGenerator.generate(), data, signal, algo.algorithmId, timeService.now())
            publisher.publish(t)
          }
          case None => // Do nothing
        }
      }
    }


  }

}
