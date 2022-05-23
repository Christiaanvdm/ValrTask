package startup

import com.google.inject.Guice
import com.google.inject.Injector
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modules.services.IRouterService

class MainVerticle : CoroutineVerticle() {
  override suspend fun start() {
    val injector: Injector = Guice.createInjector(InjectionMappingModule())
    val routerService = injector.getInstance(IRouterService::class.java)
    val routerBuilderFuture = routerService.createRouterBuilder(vertx)

    routerBuilderFuture.onSuccess { routerBuilder ->
      routerService.setupOperations(routerBuilder)
      val router = routerBuilder.createRouter()
      vertx.createHttpServer()
        .requestHandler { req ->
          launch(Dispatchers.Default) {
            router.handle(req)
          }
        }
        .listen(8888)
    }.onFailure { err ->
      throw ExceptionInInitializerError(err)
    }
  }
}

