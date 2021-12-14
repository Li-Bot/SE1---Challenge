
import Foundation

protocol SELogger {
    func log(order: Order, price: OrderPrice)
}

final class OrderPriceLogger: SELogger {
    func log(order: Order, price: OrderPrice) {
        print("Order: \(order.id). Price: \(price.price)")
    }
}
