package net.sandipan.scalazmq.tradereport

import net.sandipan.scalazmq.zmq.{ContextComponent, SubscriberComponent}
import net.sandipan.scalazmq.common.model.Trade
import net.sandipan.scalazmq.common.components.ConfigComponent
import com.typesafe.config.Config
import net.sandipan.scalazmq.tradereport.mongo.{MongoClientComponent, MongoPersistenceComponent}

class ComponentRegistry(config: Config)
  extends TradeCaptureComponent
  with SubscriberComponent[Trade]
  with ConfigComponent
  with ContextComponent
  with MongoPersistenceComponent[Trade]
  with MongoClientComponent {

  override val subscription = new Subscription

  override val configProvider = ConfigProvider(config)

  override val contextProvider = ContextComponent.defaultContextProvider(config)

  override val capturer = new TradeCapturer

  override val repository = new MongoRepository()

  override val mongoClientProvider = new MongoClientProvider
}
