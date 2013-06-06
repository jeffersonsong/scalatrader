package net.sandipan.scalazmq.zmq

import net.sandipan.scalazmq.common.components.ConfigComponent
import org.jeromq.ZMQ
import org.jeromq.ZMQ.Socket
import net.sandipan.scalazmq.common.util.HasLogger
import net.sandipan.scalazmq.zmq.serialization.{Topic, Deserializer}

trait SubscriberComponent[T] {
  this: ContextComponent with ConfigComponent =>

  def subscription: Subscription

  class Subscription(implicit deserializer: Deserializer[T], topicResolver: Topic[T]) extends HasZmqSocket with HasLogger {

    lazy val address: String = config.getString("zmq.subscribeAddress")

    lazy val socket: Socket = {
      log.debug("Connecting to %s".format(address))
      val s = contextProvider.context.socket(ZMQ.SUB)
      if (!s.connect(address))
        throw new RuntimeException("Could not open ZMQ Socket to %s".format(address))
      log.debug("Subscribing to topic %s".format(topicResolver.value))
      s.subscribe(topicResolver.value)
      s
    }

    def stream: Stream[T] = {
      socket.recvMsg() // First part of message is the topic bytes, ignoring
      if (!socket.hasReceiveMore)
        throw new RuntimeException("No more data after topic...")
      val typedItem = deserializer.deserialize(socket.recv())
      Stream.cons(typedItem, stream)
    }

  }

}
