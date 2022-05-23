package modules.services

import com.google.inject.Inject
import controllers.ITransactionBooksController
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.openapi.RouterBuilder
import io.vertx.ext.web.validation.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import types.constants.Configuration
import types.exceptions.UserOutputParseException

interface IRouterService {
  fun setupOperations(routerBuilder: RouterBuilder)
  fun createRouterBuilder(vertx: Vertx): Future<RouterBuilder>
}

open class RouterService @Inject constructor(private val _transactionBookController: ITransactionBooksController) :
  IRouterService {
  protected fun getValidationError(failure: Throwable): String {
    println("Validation error: ${failure.message}")
    return when (failure) {
      is UserOutputParseException -> "Endpoint data parsing error.\n Reason: ${failure.message}"
      is RequestPredicateException -> "Content invalid format."
      is BodyProcessorException -> "Your request body was composed incorrectly.\n Reason: ${failure.message}"
      is BadRequestException -> "Your request could not be parsed."
      else -> throw failure
    }
  }

  protected fun handleValidationErrors(ctx: RoutingContext) {
    ctx.end("Validation error. ${getValidationError(ctx.failure())}".trimEnd())
  }

  private inline fun <reified T> RouterBuilder.handleControllerRequestAsync(
    operation: String,
    noinline controllerFn: suspend (RequestParameters) -> T,
  ) {
    this
      .operation(operation)
      .handler {
        runBlocking {
          val params: RequestParameters = it.get(ValidationHandler.REQUEST_CONTEXT_KEY)
          it.end(Json.encodeToString(controllerFn(params)))
        }
      }.failureHandler {
        handleValidationErrors(it)
      }
  }

  override fun setupOperations(routerBuilder: RouterBuilder) {
    routerBuilder.handleControllerRequestAsync("getOrderBook", _transactionBookController.getOrderBook)
    routerBuilder.handleControllerRequestAsync("getTradeHistory", _transactionBookController.getTradeHistory)
    routerBuilder.handleControllerRequestAsync("postLimitOrder", _transactionBookController.postLimitOrder)
  }

  override fun createRouterBuilder(vertx: Vertx): Future<RouterBuilder> =
    RouterBuilder.create(vertx, Configuration.OpenApiSpecificationPath)
}
