package modules.helpers

import io.vertx.ext.web.validation.RequestParameter
import io.vertx.ext.web.validation.RequestParameters
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import types.exceptions.UserOutputParseException

inline fun <reified T : Enum<T>> getPathParameter(params: RequestParameters, paramName: String): T {
  val param = params.pathParameter(paramName).toString()
  try {
    return enumValueOf(param)
  } catch (ex: IllegalArgumentException) {
    print(ex)
    throw UserOutputParseException(ex.message)
  }
}

inline fun <reified T> getRequestBody(params: RequestParameters): T {
  val body: RequestParameter = params.body()
  return Json.decodeFromString(body.jsonObject.toString())
}
