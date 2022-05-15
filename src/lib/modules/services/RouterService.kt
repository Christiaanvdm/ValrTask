package modules.services

import com.google.inject.Inject
import controllers.OrderBookController
import io.vertx.ext.web.Router

class RouterService @Inject constructor(private val orderBookController: OrderBookController) : IRouterService {
  override fun compileRouter(router: Router): Router {
    router.apply {
      get("/home").handler { ctx -> orderBookController.getOrderBook(ctx) }
    }

    return router
  }
}
