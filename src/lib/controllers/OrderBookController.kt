package controllers

import io.vertx.ext.web.RoutingContext
import javax.inject.Singleton

interface IOrderBookController {
  fun getOrderBook(ctx: RoutingContext): Unit
}

@Singleton
class OrderBookController: IOrderBookController {
  override fun getOrderBook(ctx: RoutingContext) {
    ctx.response().end("Woohoo")
  }
}

