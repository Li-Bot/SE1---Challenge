
import Foundation

protocol OrderPriceGenerator {
    func multiplePrice(orderPrice: OrderPrice) -> OrderPrice
    func discountPrice(orderPrice: OrderPrice) -> OrderPrice
}

final class OrderPriceGeneratorImpl: OrderPriceGenerator {
    private var priceMultiplier: PriceMultiplier {
        .init(value: 1.5)
    }
    
    func multiplePrice(orderPrice: OrderPrice) -> OrderPrice {
        OrderPrice(price: orderPrice.price * priceMultiplier.value)
    }
    
    func discountPrice(orderPrice: OrderPrice) -> OrderPrice {
        switch orderPrice.price {
        case let price where price > 150000:
            return .init(price: price * 0.5)
        case let price where price > 100000:
            return .init(price: price * 0.3)
        default:
            return orderPrice
        }
    }
    
}
