package modules.helpers

import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.validation.RequestParameters
import io.vertx.ext.web.validation.ValidationHandler
import types.exceptions.UserOutputParseException

inline fun <reified T : Enum<T>> getQueryParam(ctx: RoutingContext, paramName: String): T {
  val params: RequestParameters = ctx.get(ValidationHandler.REQUEST_CONTEXT_KEY)
  val param = params.pathParameter(paramName).toString()
  try {
    return enumValueOf(param)
  } catch (ex: IllegalArgumentException) {
    print(ex)
    throw UserOutputParseException(ex.message)
  }
}

