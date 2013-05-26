package net.sandipan.scalazmq.zmq

import org.jeromq.ZMQ.Context

trait ContextComponent {

  def contextProvider: ContextProvider

  case class ContextProvider(val context: Context)

}
