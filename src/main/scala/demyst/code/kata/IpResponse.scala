package demyst.code.kata

import zio.json.{DeriveJsonDecoder, JsonDecoder}

case class IpResponse(ip: String)

object IpResponse {
  implicit val decoder: JsonDecoder[IpResponse] = DeriveJsonDecoder.gen[IpResponse]
}
