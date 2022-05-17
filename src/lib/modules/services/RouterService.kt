package modules.services

import com.google.inject.Inject
import controllers.ITransactionBooksController
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.openapi.RouterBuilder
import io.vertx.ext.web.validation.BadRequestException
import io.vertx.ext.web.validation.BodyProcessorException
import io.vertx.ext.web.validation.RequestPredicateException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import modules.helpers.getPathParameter
import modules.helpers.getRequestBody
import types.constants.Configuration
import types.constants.ECurrencyPair
import types.exceptions.UserOutputParseException
import types.models.query.LimitOrderRequest

interface IRouterService {
  fun setupOperations(routerBuilder: RouterBuilder)

  fun createRouterBuilder(vertx: Vertx): Future<RouterBuilder>
}

open class RouterService @Inject constructor(private val transactionBookController: ITransactionBooksController) : IRouterService {
  private fun getCurrencyPair(ctx: RoutingContext) =
    getPathParameter<ECurrencyPair>(ctx, "currencyPair")

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

  private val handleGetTradeHistory: (ctx: RoutingContext) -> Unit = {
    TODO("Implement")
  }

  private val handlePostLimitOrder: (ctx: RoutingContext) -> Unit = {
    val request: LimitOrderRequest = getRequestBody(it)
    val result = transactionBookController.postLimitOrder(request)
    it.end(Json.encodeToString(result))
  }

  protected val handleGetOrderBook: (ctx: RoutingContext) -> Unit = {
    val currencyPair = getCurrencyPair(it)
    val result = transactionBookController.getOrderBook(currencyPair)
    it.end(Json.encodeToString(result))
  }

  override fun setupOperations(routerBuilder: RouterBuilder) {
    routerBuilder.handleOperation("getOrderBook", handleGetOrderBook)
    routerBuilder.handleOperation("getTradeHistory", handleGetTradeHistory)
    routerBuilder.handleOperation("postLimitOrder", handlePostLimitOrder)
  }

  override fun createRouterBuilder(vertx: Vertx): Future<RouterBuilder> =
    RouterBuilder.create(vertx, Configuration.OpenApiSpecificationPath)
}
