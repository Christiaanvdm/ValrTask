package modules.services

import io.vertx.ext.web.Router

interface IRouterService {
  fun compileRouter(router: Router): Router
}
