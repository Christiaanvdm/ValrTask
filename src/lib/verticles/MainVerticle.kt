package verticles

import com.google.inject.Guice
import com.google.inject.Injector
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.ext.web.Router
import com.google.inject.AbstractModule
import controllers.OrderBookController
import modules.services.IRouterService
import modules.services.RouterService

class MainVerticle : AbstractVerticle() {
  override fun start(startPromise: Promise<Void>) {
    val injector: Injector = Guice.createInjector(MainGuiceModule())
    val routerService = injector.getInstance(IRouterService::class.java)

    var router = Router.router(vertx)
    router = routerService.compileRouter(router)
    router.apply {
      get("/*").handler { ctx ->
        ctx.response().setStatusCode(404).end("Invalid Route. Please confirm the URL supplied.")
      }
    }

    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(8888) { http ->
        if (http.succeeded()) {
          println("HTTP server started on port 8888")
          startPromise.complete()
        } else {
          println("HTTP server failed to start on port 8888")
          startPromise.fail(http.cause())
        }
      }
  }
}

class MainGuiceModule : AbstractModule() {
  override fun configure() {
    bind(IRouterService::class.java).to(RouterService::class.java)
    bind(OrderBookController::class.java)
  }
}
