package net.sandipan.scalazmq.zmq

import org.jeromq.ZMQ.Socket

trait HasZmqSockets {

  @volatile private var allSockets: List[Socket] = List()

  def registerSocket(socket: Socket) {
    allSockets ::= socket
  }

  def closeAllSockets() {
    allSockets map (_.close)
  }

}
