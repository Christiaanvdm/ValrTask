package startup

import development.MockStore
import com.google.inject.AbstractModule
import controllers.ITransactionBooksController
import controllers.TransactionBooksController
import modules.providers.ILimitOrderBookProvider
import modules.providers.IOrderBookProvider
import modules.providers.LimitOrderBookProvider
import modules.providers.OrderBookProvider
import modules.services.*
import types.models.store.IStore

class InjectionMappingModule : AbstractModule() {
  override fun configure() {
    bind(IRouterService::class.java).to(RouterService::class.java)
    bind(ITransactionBooksController::class.java).to(TransactionBooksController::class.java)
    bind(IOrderBookProvider::class.java).to(OrderBookProvider::class.java)
    bind(ILimitOrderBookProvider::class.java).to(LimitOrderBookProvider::class.java)
    bind(IStoreService::class.java).to(StoreService::class.java)
    bind(IStore::class.java).to(MockStore::class.java)
    bind(INextIdService::class.java).to(NextIdService::class.java)
    }
}
