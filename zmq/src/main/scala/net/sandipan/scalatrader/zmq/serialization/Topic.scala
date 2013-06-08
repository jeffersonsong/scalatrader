package net.sandipan.scalatrader.zmq.serialization

import net.sandipan.scalatrader.common.model.{MarketData, Trade}

sealed trait Topic[T] {
  def value: String
}

object Topic {

  val TOPIC_VALUES: Map[Class[_], String] = Map(
    (Trade.getClass -> "trade"),
    (MarketData.getClass -> "marketdata")
  )

  class TopicValueFetcher(cls: Class[_]) {
    def value = TOPIC_VALUES(cls)
  }

  implicit object tradeTopic extends TopicValueFetcher(Trade.getClass) with Topic[Trade]

  implicit object marketDataTopic extends TopicValueFetcher(MarketData.getClass) with Topic[MarketData]

}