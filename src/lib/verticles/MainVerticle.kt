package verticles

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.AbstractModule
import controllers.IOrderBookController
import controllers.OrderBookController
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineVerticle
import modules.services.IRouterService
import modules.services.RouterService

class MainVerticle : CoroutineVerticle() {
  private fun createServer(router: Router): HttpServer {
    return vertx.createHttpServer().requestHandler(router).listen(8888) { http ->
      if (http.succeeded()) {
        println("HTTP server started on port 8888")
      } else {
        println("HTTP server failed to start on port 8888")
      }
    }
  }

  override suspend fun start() {
    val injector: Injector = Guice.createInjector(MainGuiceModule())
    val routerService = injector.getInstance(IRouterService::class.java)
    val routerBuilderFuture = routerService.createRouterBuilder(vertx)

    routerBuilderFuture.onSuccess { routerBuilder ->
      routerService.setupOperations(routerBuilder)
      createServer(routerBuilder.createRouter())
    }.onFailure { err ->
      throw ExceptionInInitializerError(err)
    }
  }
}

class MainGuiceModule : AbstractModule() {
  override fun configure() {
    bind(IRouterService::class.java).to(RouterService::class.java)
    bind(IOrderBookController::class.java).to(OrderBookController::class.java)
  }
}
