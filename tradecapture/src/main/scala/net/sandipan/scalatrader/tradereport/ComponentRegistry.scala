package net.sandipan.scalatrader.tradereport

import net.sandipan.scalatrader.zmq.{ContextComponent, SubscriberComponent}
import net.sandipan.scalatrader.common.model.Trade
import net.sandipan.scalatrader.common.components.ConfigComponent
import com.typesafe.config.Config
import net.sandipan.scalatrader.tradereport.mongo.{MongoClientComponent, MongoPersistenceComponent}

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

  override val mongoClientProvider = new MongoClientProvider

  override val repository = new MongoRepository

}
