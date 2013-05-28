package net.sandipan.scalazmq.zmq

import com.typesafe.config.Config
import org.jeromq.ZMQ
import net.sandipan.scalazmq.common.serialization.Serializer
import net.sandipan.scalazmq.common.components.ConfigComponent
import net.sandipan.scalazmq.common.util.HasLogger

trait PublisherComponent[T] {
  this: ContextComponent with ConfigComponent =>

  def publisher: Publisher[T]

  class Publisher[T] extends HasZmqSocket with HasLogger {

    private val socketAddr = config.getString(PublisherComponent.ADDRESS)

    override val socket = {
      val s = contextProvider.context.socket(ZMQ.PUB)
      if (!s.connect(socketAddr))
        throw new RuntimeException("Could not connect to ZMQ socket %s".format(socketAddr))
      s
    }

    def publish(data: T)(implicit serializer: Serializer[T]) {
      val result = socket.send(serializer.serialize(data))
      if (!result)
        log.error("Oops.. unable to send %s".format(data))
    }

  }

}

object PublisherComponent {

  val ADDRESS = "publishAddress"

}