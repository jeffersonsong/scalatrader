package net.sandipan.scalazmq.zmq

import org.jeromq.ZMQ.Socket

trait HasZmqSocket {

  def socket: Socket

  def closeSocket() {
    socket.close()
  }

}
