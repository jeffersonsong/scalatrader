package net.sandipan.scalatrader.tradereport

import net.sandipan.scalatrader.common.model.Id
import net.sandipan.scalatrader.common.util.HasLogger

trait PersistenceComponent[T <: Id[String]] {

  def repository: Repository

  trait Repository {
    def persist(item: T)
  }

  class InMemoryRepository extends Repository  with HasLogger {

    private var data: Map[T#Type, T] = Map()

    def persist(item: T) {
      data += (item.id -> item)
      log.debug("Inserting: %s".format(item))
    }

  }

}
