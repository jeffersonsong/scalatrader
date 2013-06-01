package net.sandipan.scalazmq.zmq

import net.sandipan.scalazmq.common.serialization.Deserializer
import net.sandipan.scalazmq.common.components.ConfigComponent
import org.jeromq.ZMQ
import org.jeromq.ZMQ.Socket

trait SubscriberComponent[T] {
  this: ContextComponent with ConfigComponent =>

  def subscription: Subscription

  class Subscription extends HasZmqSocket {

    lazy val address: String = config.getString("subscribeAddress")

    lazy val socket: Socket = {
      val s = contextProvider.context.socket(ZMQ.XSUB)
      if (!s.connect(address))
        throw new RuntimeException("Could not open ZMQ Socket to %s".format(address))
      s
    }

    def stream(implicit d: Deserializer[T]): Stream[T] = {
      val bytes = socket.recvMsg().data()
      val typedItem = d.deserialize(bytes)
      Stream.cons(typedItem, stream)
    }

  }

}
