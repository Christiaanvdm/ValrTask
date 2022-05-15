package modules.verticles

import com.google.inject.Guice
import com.google.inject.Injector
import io.mockk.*
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import modules.services.IRouterService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import verticles.MainVerticle
import com.google.inject.Module

@ExtendWith(VertxExtension::class)
class TestMainVerticle {
  private val _routerServiceMock = mockk<IRouterService>()
  private var _routerObject: Router? = null

  @BeforeEach
  fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
    _routerObject = Router.router((vertx))
    mockkStatic(Router::class)
    every { Router.router(any()) } returns _routerObject

    val injectMock = mockk<Injector>()
    every { injectMock.getInstance(IRouterService::class.java) } returns _routerServiceMock

    mockkStatic((Guice::class))
    every { Guice.createInjector(any<Module>()) } returns injectMock

    every { _routerServiceMock.compileRouter(any()) } returns Router.router(vertx)
    vertx.deployVerticle(MainVerticle(), testContext.succeeding<String> { _ -> testContext.completeNow() })
  }

  @Test
  fun verticle_deployed(vertx: Vertx, testContext: VertxTestContext) {
    testContext.completeNow()

    verify { _routerServiceMock.compileRouter(_routerObject!!) }
    confirmVerified(_routerServiceMock)
  }

  @AfterEach
  fun destroy() {
    unmockkStatic(Router::class)
  }
}
