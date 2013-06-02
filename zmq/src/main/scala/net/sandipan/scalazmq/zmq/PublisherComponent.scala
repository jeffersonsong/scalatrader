package net.sandipan.scalazmq.zmq

import com.typesafe.config.Config
import org.jeromq.ZMQ
import net.sandipan.scalazmq.common.components.ConfigComponent
import net.sandipan.scalazmq.common.util.HasLogger
import net.sandipan.scalazmq.zmq.serialization.{Topic, Serializer}

trait PublisherComponent[T] {
  this: ContextComponent with ConfigComponent =>

  def publisher: Publisher

  class Publisher extends HasZmqSocket with HasLogger {

    private val socketAddr = config.getString(PublisherComponent.ADDRESS)

    override val socket = {
      val s = contextProvider.context.socket(ZMQ.PUB)
      if (s.bind(socketAddr) < 0)
        throw new RuntimeException("Could not BIND to ZMQ socket %s".format(socketAddr))
      s
    }

    def publish(data: T)(implicit serializer: Serializer[T], topic: Topic[T]) {
      if (!socket.sendMore(topic.value()))
        log.error("Oops.. unable to send topic information.")

      if (!socket.send(serializer.serialize(data)))
        log.error("Oops.. unable to send %s".format(data))
    }

  }

}

object PublisherComponent {

  val ADDRESS = "zmq.publishAddress"

}