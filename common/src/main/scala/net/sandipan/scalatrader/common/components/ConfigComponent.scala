package net.sandipan.scalatrader.common.components

import com.typesafe.config.Config

trait ConfigComponent {

  def config: Config = configProvider.config

  def configProvider: ConfigProvider

  case class ConfigProvider(config: Config)

}
