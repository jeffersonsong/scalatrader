package net.sandipan.scalazmq.zmq

import org.jeromq.ZMQ.{Poller, Socket}
import org.jeromq.ZMQ
import net.sandipan.scalazmq.common.components.ConfigComponent
import com.typesafe.config.Config
import net.sandipan.scalazmq.common.util.HasLogger

trait MessageBrokerComponent {
  this: ContextComponent with ConfigComponent =>

  def brokerComponent: MessageBroker

  class MessageBroker extends HasLogger {

    lazy val xpubSocket: Socket = contextProvider.context.socket(ZMQ.XPUB)
    lazy val xsubSocket: Socket = contextProvider.context.socket(ZMQ.XSUB)

    /**
     * Inspiration:
     * http://zguide.zeromq.org/java:rrbroker
     */
    def start() {

      val xpubAddr = config.getString(MessageBrokerComponent.XPUB_ADDR)
      val xsubAddr = config.getString(MessageBrokerComponent.XSUB_ADDR)

      log.info("Setting up message broker.")

      log.debug("BIND to " + xpubAddr)
      if (xpubSocket.bind(xpubAddr) < 0)
        throw new RuntimeException("Broker could not open XPUB")

      log.debug("CONNECT to " + xsubAddr)
      if (!xsubSocket.connect(xsubAddr))
        throw new RuntimeException("Broker could not open XSUB")

      val items = contextProvider.context.poller(2)
      items.register(xpubSocket, Poller.POLLIN)
      items.register(xsubSocket, Poller.POLLIN)

      log.info("Starting message broker.")

      while (!Thread.currentThread.isInterrupted) {
        items.poll()

        if (items.pollin(0)) {
          log.debug("Received a subscription on the XPUB socket - brokering.")
          performBrokering(xpubSocket, xsubSocket)
        }

        if (items.pollin(1)) {
          log.debug("Received a publish on the XSUB socket - brokering.")
          performBrokering(xsubSocket, xpubSocket)
        }

      }
    }

    private def performBrokering(in: Socket, out: Socket) {
      var moreData = false
      while (true) {
        val message = in.recv(0)
        moreData = in.hasReceiveMore
        out.send(message, if (moreData) ZMQ.SNDMORE else 0)
        if (!moreData) {
          return
        }
      }
    }

    def closeAllSockets() {
      xsubSocket.close()
      xpubSocket.close()
    }

  }
}

object MessageBrokerComponent {
  val XSUB_ADDR = "zmq.xsubAddress"
  val XPUB_ADDR = "zmq.xpubAddress"
}