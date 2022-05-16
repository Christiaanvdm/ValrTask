package modules.verticles

import com.google.inject.Guice
import com.google.inject.Injector
import io.mockk.*
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import modules.services.IRouterService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import startup.MainVerticle
import com.google.inject.Module

@ExtendWith(VertxExtension::class)
class TestMainVerticleIntegrated {
  @BeforeEach
  fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
    vertx.deployVerticle(MainVerticle(), testContext.succeeding{ testContext.completeNow() })
  }

  @Test
  fun verticle_deployed(vertx: Vertx, testContext: VertxTestContext) {
    testContext.completeNow()
  }
}

@ExtendWith(VertxExtension::class)
class TestMainVerticleUnit {
  private val _routerServiceMock = mockk<IRouterService>()
  private var _routerObject: Router? = null

  @BeforeEach
  fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
    _routerObject = Router.router((vertx))
    mockkStatic(Router::class)
    every { Router.router(any()) } returns _routerObject

    val injectMock = mockk<Injector>()
    every { injectMock.getInstance(IRouterService::class.java) } returns _routerServiceMock

    mockkStatic(Guice::class)
    every { Guice.createInjector(any<Module>()) } returns injectMock

    vertx.deployVerticle(MainVerticle(), testContext.succeeding { testContext.completeNow() })
  }

  @Test
  fun verticle_deployed_calls_createRouteBuilder(vertx: Vertx, testContext: VertxTestContext) {
    testContext.completeNow()

    verify { _routerServiceMock.createRouterBuilder(vertx) }
  }
}
