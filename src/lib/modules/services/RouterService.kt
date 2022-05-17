package modules.services

import CurrencyPair
import com.google.inject.Inject
import controllers.ITransactionBooksController
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.openapi.RouterBuilder
import io.vertx.ext.web.validation.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import modules.helpers.getQueryParam
import types.constants.Configuration
import types.exceptions.UserOutputParseException

interface IRouterService {
  fun setupOperations(routerBuilder: RouterBuilder)

  fun createRouterBuilder(vertx: Vertx): Future<RouterBuilder>
}

open class RouterService @Inject constructor(private val orderBookController: ITransactionBooksController) : IRouterService {
  private fun getCurrencyPair(ctx: RoutingContext) =
    getQueryParam<CurrencyPair>(ctx, "currencyPair")

  private fun RouterBuilder.handleOperation(operation: String, handlerFn: (ctx: RoutingContext) -> Unit) {
    this
      .operation(operation)
      .handler(handlerFn)
      .failureHandler { ctx ->
        handleValidationErrors(ctx)
      }
  }

  protected fun getValidationError(failure: Throwable): String {
    return when (failure) {
      is UserOutputParseException -> "Endpoint data parsing error. ${failure.message}"
      is RequestPredicateException -> "Content invalid format."
      is BodyProcessorException -> "Your request body was composed incorrectly."
      is BadRequestException -> "Your request could not be parsed."
      else -> {
        print(failure.message); throw failure
      }
    }
  }

  protected fun handleValidationErrors(ctx: RoutingContext) {
    ctx.end("Validation error. ${getValidationError(ctx.failure())}".trimEnd())
  }

  protected val handleGetOrderBook: (ctx: RoutingContext) -> Unit = {
    val currencyPair = getCurrencyPair(it)
    val result = orderBookController.getOrderBook(currencyPair)
    it.end(Json.encodeToString(result))
  }

  protected val handleGetTradeHistory: (ctx: RoutingContext) -> Unit = {
    TODO("")
  }

  protected val handlePostLimitOrder: (ctx: RoutingContext) -> Unit = {
    TODO("")
  }

  override fun setupOperations(routerBuilder: RouterBuilder) {
    routerBuilder.handleOperation("getOrderBook", handleGetOrderBook)
    routerBuilder.handleOperation("getTradeHistory", handleGetTradeHistory)
    routerBuilder.handleOperation("postLimitOrder", handlePostLimitOrder)
  }

  override fun createRouterBuilder(vertx: Vertx): Future<RouterBuilder> =
    RouterBuilder.create(vertx, Configuration.OpenApiSpecificationPath)
}
