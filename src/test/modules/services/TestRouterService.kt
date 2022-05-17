import controllers.ITransactionBooksController
import io.mockk.*
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.openapi.RouterBuilder
import io.vertx.ext.web.validation.BadRequestException
import modules.services.IRouterService
import modules.services.RouterService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import types.constants.Configuration
import types.exceptions.UserOutputParseException
import java.io.InvalidObjectException

class RouterServiceExposed(orderBookController: ITransactionBooksController) : RouterService(orderBookController) {
  fun handleValidationErrorsExposed(ctx: RoutingContext) =
    super.handleValidationErrors(ctx)

  fun getValidationErrorExposed(failure: Throwable): String =
    super.getValidationError(failure)
}

class TestRouterService {
  private val _orderBookControllerMock: ITransactionBooksController = mockk()
  private val _exposed = RouterServiceExposed(_orderBookControllerMock)
  private val _routerService: IRouterService = _exposed

  @Test
  fun createRouteBuilder_ReturnsExpectedResult() {
    val mockRouterBuilderFuture = mockk<Future<RouterBuilder>>()
    val mockVertx = mockk<Vertx>()

    mockkStatic(RouterBuilder::class)
    every { RouterBuilder.create(any(), any()) } returns mockRouterBuilderFuture

    _routerService.createRouterBuilder(mockVertx)

    verify { RouterBuilder.create(mockVertx, Configuration.OpenApiSpecificationPath) }
  }

  @Test
  fun handleValidationErrors_ends_context() {
    val validationError = "[ValidationErrorMessage]"
    val exposedSpy = spyk(_exposed, recordPrivateCalls = true)
    val ctx = mockk<RoutingContext>()
    val exception = mockk<BadRequestException>()
    every { exposedSpy["getValidationError"](exception) } returns validationError
    every { ctx.failure() } returns exception
    every { ctx.end(any<String>()) } returns mockk<Future<Void>>()

    exposedSpy.handleValidationErrorsExposed(ctx)

    verify {
      ctx.end(withArg<String> {
        assertTrue(validationError in it)
      })
    }
  }

  //region getValidationError

  @Test
  fun getValidationError_includesMessage() {
    val expected = "Custom Message"

    val result = _exposed.getValidationErrorExposed(UserOutputParseException(expected))

    assertTrue(expected in result)
  }

  @Test
  fun getValidationError_ThrowsExceptionIfNotHandled() {
    assertThrows<InvalidObjectException> { _exposed.getValidationErrorExposed(InvalidObjectException("")) }
  }

  //endregion
}

