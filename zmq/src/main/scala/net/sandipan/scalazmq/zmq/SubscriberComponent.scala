package net.sandipan.scalazmq.zmq

import net.sandipan.scalazmq.common.serialization.Deserializer
import net.sandipan.scalazmq.common.components.ConfigComponent

trait SubscriberComponent[T] {
  this: ContextComponent with ConfigComponent =>

  def subscription: Subscription

  class Subscription {

    def stream(implicit d: Deserializer[T]): Stream[T] {
      // Subscribe and receive stream data....
    }

  }

}
