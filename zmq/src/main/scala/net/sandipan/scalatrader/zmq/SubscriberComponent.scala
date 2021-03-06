package net.sandipan.scalatrader.zmq

import net.sandipan.scalatrader.common.components.ConfigComponent
import org.jeromq.ZMQ
import org.jeromq.ZMQ.Socket
import net.sandipan.scalatrader.common.util.HasLogger
import net.sandipan.scalatrader.zmq.serialization.{Topic, Deserializer}

trait SubscriberComponent[T] {
  this: ContextComponent with ConfigComponent =>

  def subscription: Subscription

  class Subscription(implicit deserializer: Deserializer[T], topicResolver: Topic[T]) extends HasLogger with HasZmqSockets {

    private lazy val address: String = config.getString("zmq.subscribeAddress")

    private val socket = new ThreadLocal[Socket]() {
      override def initialValue(): Socket = {
        log.info("Connecting to %s".format(address))
        val s = contextProvider.context.socket(ZMQ.SUB)
        if (!s.connect(address))
          throw new RuntimeException("Could not open ZMQ Socket to %s".format(address))
        log.info("Subscribing to topic %s".format(topicResolver.value))
        s.subscribe(topicResolver.value)
        registerSocket(s)
        s
      }
    }

    def stream: Stream[T] = {
      socket.get.recvMsg() // First part of message is the topic bytes, ignoring
      if (!socket.get.hasReceiveMore)
        throw new RuntimeException("No more data after topic...")
      val typedItem = deserializer.deserialize(socket.get.recv())
      Stream.cons(typedItem, stream)
    }

  }

}
