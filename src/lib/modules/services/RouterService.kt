package modules.services

import io.vertx.ext.web.Router
import jakarta.inject.Singleton

@Singleton
class RouterService: IRouterService {
  override fun compileRouter(router: Router): Router {
    TODO("Not yet implemented")
  }
}
