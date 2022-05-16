package startup

import com.google.inject.Guice
import com.google.inject.Injector
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineVerticle
import modules.services.IRouterService

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
    val injector: Injector = Guice.createInjector(InjectionMappingModule())
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

