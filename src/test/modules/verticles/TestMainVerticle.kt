package modules.verticles

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

@ExtendWith(VertxExtension::class)
class TestMainVerticle {
  private val _routerCompilationServiceMock = mockk<IRouterService>()
  private var _routerObject: Router? = null

  @BeforeEach
  fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
    _routerObject = Router.router((vertx))
    mockkStatic(Router::class)
    every { Router.router(any()) } returns _routerObject

    every { _routerCompilationServiceMock.compileRouter(any()) } returns Router.router(vertx)
    vertx.deployVerticle(MainVerticle(_routerCompilationServiceMock), testContext.succeeding<String> { _ -> testContext.completeNow() })
  }

  @Test
  fun verticle_deployed(vertx: Vertx, testContext: VertxTestContext) {
    testContext.completeNow()

    verify { _routerCompilationServiceMock.compileRouter(_routerObject!!) }
    confirmVerified(_routerCompilationServiceMock)
  }

  @AfterEach
  fun destroy() {
    unmockkStatic(Router::class)
  }
}
