package demyst.code.kata

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import zio._
import zio.http._

class CodeKataSpec extends AnyFlatSpec with Matchers {
  val mockClient = mock(classOf[Client])
  val mockClient2 = mock(classOf[Client])
  val mockResponse = mock(classOf[Response])
  val ipResponse = IpResponse("123.456.789.000")

  "CodeKata" should "return the correct IP address from the API" in {
    val jsonResponse = s"""{"ip":"${ipResponse.ip}"}"""

    when(mockResponse.body).thenReturn(Body.fromString(jsonResponse))
    when(mockClient.url(any[URL])).thenReturn(mockClient2)
    when(mockClient2.get(any[String])(any[Body <:< Body], any[Trace])).thenReturn(ZIO.succeed(mockResponse))

    val program = CodeKata.program.provide(ZLayer.succeed(mockClient), Scope.default)

    val result = Unsafe.unsafe { implicit u =>
      Runtime.default.unsafe.run(program).getOrThrowFiberFailure()
    }

    result shouldBe ipResponse
  }

  it should "return an error if the response body is invalid" in {
    val jsonResponse = s""""${ipResponse.ip}"""

    when(mockResponse.body).thenReturn(Body.fromString(jsonResponse))
    when(mockClient.url(any[URL])).thenReturn(mockClient2)
    when(mockClient2.get(any[String])(any[Body <:< Body], any[Trace])).thenReturn(ZIO.succeed(mockResponse))

    val program = CodeKata.program.provide(ZLayer.succeed(mockClient), Scope.default)

    val caught = intercept[Throwable] {
      Unsafe.unsafe { implicit u =>
        Runtime.default.unsafe.run(program).getOrThrowFiberFailure()
      }
    }

    caught.getMessage should startWith("Failed to parse IP response")
  }

}
