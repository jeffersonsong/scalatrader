package net.sandipan.scalazmq.tradereport.mongo

import net.sandipan.scalazmq.tradereport.PersistenceComponent
import net.sandipan.scalazmq.common.model.Id
import com.mongodb.casbah.{MongoDB, MongoCollection}
import com.mongodb.casbah.Imports._

trait MongoPersistenceComponent[T <: Id[String]] extends PersistenceComponent[T] {
  this: MongoClientComponent =>

  class MongoRepository(dbName: String, collectionName: String, mongoConverter: MongoConverter[T]) extends Repository {

    private val db: MongoDB = mongoClient(dbName)

    private val collection: MongoCollection = db(collectionName)

    def persist(item: T) {
      val obj = mongoConverter.convert(item)
      collection.insert(obj)
    }

    def retrieve(id: T#Type): T = {
      collection.findOneByID(id)
    }

  }

}


object MongoPersistenceComponent {

  def createMongoRepository[T](dbName: String, collectionName: String)(implicit converter: MongoConverter[T]) =
    new MongoPersistenceComponent#MongoRepository(dbName, collectionName, converter)

}