package net.sandipan.scalazmq.zmq.serialization

import net.sandipan.scalazmq.common.model.{MarketData, Trade}
import java.nio.ByteBuffer

sealed trait Topic[T] {
  def value(): Array[Byte]
}

object Topic {

  val TOPIC_LENGTH = 4

  val TOPIC_VALUES: Map[Class[_], Int] = Map(
    (Trade.getClass -> 1),
    (MarketData.getClass -> 2)
  )

  private def convertToByteArray(i: Int): Array[Byte] = ByteBuffer.allocate(TOPIC_LENGTH).putInt(i).array()

  class TopicValueFetcher(cls: Class[_]) {
    def value(): Array[Byte] = {
      convertToByteArray(TOPIC_VALUES(cls))
    }
  }

  implicit object tradeTopic extends TopicValueFetcher(Trade.getClass) with Topic[Trade]

  implicit object marketDataTopic extends TopicValueFetcher(MarketData.getClass) with Topic[MarketData]

}