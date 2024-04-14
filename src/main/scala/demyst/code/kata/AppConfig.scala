package demyst.code.kata

import com.typesafe.config.ConfigFactory
import zio.Config.string
import zio.{Config, ConfigProvider}
import zio.config._
import zio.config.syntax.Read
import zio.config.typesafe.FromConfigSourceTypesafe

case class AppConfig(url: String)

object AppConfig {
  val appConfig: Config[AppConfig] = string("url").to[AppConfig]
  val configProvider: ConfigProvider = ConfigProvider.fromTypesafeConfig(ConfigFactory.load().resolve())
  val configF: Read[AppConfig] = AppConfig.appConfig from configProvider
}
