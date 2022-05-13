import java.util.Date

class OrderBookResult(
  var lastChange: Date,
  var sequenceNumber: Long,
  var asks: Array<Order>,
  var bids: Array<Order>,
)
