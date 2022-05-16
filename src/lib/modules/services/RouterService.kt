package modules.services

import CurrencyPair
import com.google.inject.Inject
import controllers.IOrderBookController
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

open class RouterService @Inject constructor(private val orderBookController: IOrderBookController) : IRouterService {
  private fun getCurrencyPair(ctx: RoutingContext) =
    getQueryParam<CurrencyPair>(ctx, "currencyPair")

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

  protected fun handleGetOrderBook(ctx: RoutingContext) {
    val currencyPair = getCurrencyPair(ctx)
    val result = orderBookController.getOrderBook(currencyPair)
    ctx.end(Json.encodeToString(result))
  }

  override fun setupOperations(routerBuilder: RouterBuilder) {
    routerBuilder
      .operation("getOrderBook")
      .handler { ctx ->
        handleGetOrderBook(ctx)
      }
      .failureHandler { ctx ->
        handleValidationErrors(ctx)
      }
  }

  override fun createRouterBuilder(vertx: Vertx): Future<RouterBuilder> =
    RouterBuilder.create(vertx, Configuration.OpenApiSpecificationPath)
}
