package startup

import com.google.inject.AbstractModule
import controllers.ITransactionBooksController
import controllers.TransactionBooksController
import development.MockStore
import modules.providers.*
import modules.services.*
import types.models.store.IStore

class InjectionMappingModule : AbstractModule() {
  override fun configure() {
    bind(IStore::class.java).to(MockStore::class.java)
    bind(IDataService::class.java).to(DataService::class.java)
    bind(INextIdService::class.java).to(NextIdService::class.java)

    bind(IOrderBookProvider::class.java).to(OrderBookProvider::class.java)
    bind(ILimitOrderBookProvider::class.java).to(LimitOrderBookProvider::class.java)
    bind(ITradeBookProvider::class.java).to(TradeBookProvider::class.java)

    bind(ITransactionBooksController::class.java).to(TransactionBooksController::class.java)

    bind(IRouterService::class.java).to(RouterService::class.java)
  }
}
