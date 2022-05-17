package modules.services

import com.google.inject.Inject
import controllers.ITransactionBooksController
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.openapi.RouterBuilder
import io.vertx.ext.web.validation.*
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
  private inline fun <reified T> handleRequest(ctx: RoutingContext, controllerFn: (params: RequestParameters) -> T) {
    val params: RequestParameters = ctx.get(ValidationHandler.REQUEST_CONTEXT_KEY)
    val result = controllerFn(params)
    ctx.end(Json.encodeToString(result))
  }

  private fun RouterBuilder.handleOperation(operation: String, handlerFn: (ctx: RoutingContext) -> Unit) {
    this
      .operation(operation)
      .handler(handlerFn)
      .failureHandler { ctx ->
        handleValidationErrors(ctx)
      }
  }

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

  override fun setupOperations(routerBuilder: RouterBuilder) {
    routerBuilder.handleOperation(
      "getOrderBook",
    ) { handleRequest(it, _transactionBookController.getOrderBook) }
    routerBuilder.handleOperation(
      "getTradeHistory",
    ) { handleRequest(it, _transactionBookController.getTradeHistory) }
    routerBuilder.handleOperation(
      "postLimitOrder",
    ) { handleRequest(it, _transactionBookController.postLimitOrder) }
  }

  override fun createRouterBuilder(vertx: Vertx): Future<RouterBuilder> =
    RouterBuilder.create(vertx, Configuration.OpenApiSpecificationPath)
}
