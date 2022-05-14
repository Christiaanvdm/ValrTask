package verticles

import jakarta.inject.Inject
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.ext.web.Router
import modules.services.IRouterService

class MainVerticle(@Inject var routerCompilationService: IRouterService): AbstractVerticle() {
  override fun start(startPromise: Promise<Void>) {
    var router = Router.router(vertx)
    router = routerCompilationService.compileRouter(router)
    router.apply {
      get("/*").handler {
          ctx -> ctx.response().setStatusCode(404).end("Invalid Route. Please confirm the URL supplied")
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
          startPromise.fail(http.cause());
        }
      }
  }
}
