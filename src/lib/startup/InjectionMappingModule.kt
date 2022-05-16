package startup

import com.google.inject.AbstractModule
import controllers.IOrderBookController
import controllers.OrderBookController
import modules.providers.IOrderBookProvider
import modules.providers.OrderBookProvider
import modules.services.IRouterService
import modules.services.IStoreService
import modules.services.RouterService
import modules.services.StoreService
import types.models.store.IStore
import types.models.store.Store

class InjectionMappingModule : AbstractModule() {
  override fun configure() {
    bind(IRouterService::class.java).to(RouterService::class.java)
    bind(IOrderBookController::class.java).to(OrderBookController::class.java)
    bind(IOrderBookProvider::class.java).to(OrderBookProvider::class.java)
    bind(IStoreService::class.java).to(StoreService::class.java)
    bind(IStore::class.java).to(Store::class.java)
  }
}
