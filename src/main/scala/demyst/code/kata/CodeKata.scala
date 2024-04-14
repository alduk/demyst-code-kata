package demyst.code.kata

/*
Exercise
The goal of the project is to build a command line tool.

Using Scala (and SBT), write a command line tool that consumes the API at https://api.ipify.org/?format=json and returns just the IP address part of it.

Ensure you are submitting the code along with cli.

Judging Criteria
Engineering principles & standards
System extensibility & Scalability
Test coverage
Brevity and Simplicity
Bonus Points
Docker
Using an effect system like ZIO
 */

import zio._
import zio.config._
import zio.http._
import zio.json._

object CodeKata extends ZIOAppDefault {

  val program: ZIO[Any & Scope with Client, String, IpResponse] = for {
    config <- read(AppConfig.configF)
      .catchAll(err => ZIO.fail(s"Failed to read config: ${err.getMessage}"))
    url <- ZIO.fromEither(URL.decode(config.url))
      .catchAll(err => ZIO.fail(s"Invalid URL ${config.url}: ${err.getMessage}"))
    client <- ZIO.service[Client]
    res <- client.url(url).get("/")
      .catchAll(err => ZIO.fail(s"HTTP request failed: ${err.getMessage}"))
    resS <- res.body.asString
      .catchAll(err => ZIO.fail(s"Failed to read response body: ${err.getMessage}"))
    ipResponse <- ZIO.fromEither(resS.fromJson[IpResponse])
      .mapError(err => s"Failed to parse IP response $resS: $err")
    _ <- Console.printLine(s"IP: ${ipResponse.ip}")
      .mapError(err => s"Failed to print IP address: ${err.getMessage}")
  } yield ipResponse


  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    program
      .tapError(err => Console.printLine(err) *> exit(ExitCode.failure))
      .provide(Client.default, Scope.default)
}
