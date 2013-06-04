package net.sandipan.scalazmq.tradereport

import net.sandipan.scalazmq.common.model.Id

trait PersistenceComponent[T <: Id[String]] {

  def repository: Repository

  trait Repository {
    def persist(item: T)
    def retrieve(id: T#Type): T
  }

  class InMemoryRepository extends Repository {

    type PersistenceNativeType = T

    private var data: Map[T#Type, T] = Map()

    def persist(item: T) {
      data += (item.id -> item)
    }

    def retrieve(id: T#Type): T = data(id)

  }

}
