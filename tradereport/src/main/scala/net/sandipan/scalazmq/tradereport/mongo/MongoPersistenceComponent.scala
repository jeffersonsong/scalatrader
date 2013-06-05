package net.sandipan.scalazmq.tradereport.mongo

import net.sandipan.scalazmq.tradereport.PersistenceComponent
import net.sandipan.scalazmq.common.model.{Persistable, Id}
import com.mongodb.casbah.{MongoDB, MongoCollection}
import com.mongodb.casbah.Imports._
import net.sandipan.scalazmq.common.components.ConfigComponent

trait MongoPersistenceComponent[T <: Id[String]] extends PersistenceComponent[T] {

  this: MongoClientComponent
    with ConfigComponent =>

  class MongoRepository(implicit mongoConverter: MongoConverter[T], persistableKey: Persistable[T]) extends Repository {

    private val db: MongoDB = mongoClient(config.getString("mongo.dbName"))

    private val collection: MongoCollection = db(persistableKey.entityName)

    override def persist(item: T) {
      val obj = mongoConverter.convert(item)
      collection.insert(obj)
    }

    override def retrieve(id: T#Type): Option[T] = {
      // TODO
      ???
    }

  }

}