package net.sandipan.scalazmq.common.model

import _root_.net.sandipan.scalazmq.common.protos.Messages
import _root_.net.sandipan.scalazmq.common.serialization.Serializer
import com.google.protobuf.ByteString
import net.sandipan.scalazmq.common.services.TimeService
import org.joda.time.LocalDateTime

/**
 * Simplified MarketData
 */
case class MarketData(symbol: String, bid: Double, ask: Double, volume: Int)

object MarketData {

  implicit object marketDataSerializer extends Serializer[MarketData] {
    override def serialize(md: MarketData): Array[Byte] = {
      val proto = Messages.MarketData.newBuilder()
        .setAsk(md.ask)
        .setBid(md.bid)
        .setSymbol(md.symbol)
        .setVolume(md.volume)
        .setTimestamp(LocalDateTime.now().toString) // TODO: Remove this indeterminism. Use a centralised timeService component.
        .build()

      proto.toByteString().toByteArray()
    }
  }

}