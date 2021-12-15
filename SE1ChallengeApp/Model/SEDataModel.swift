
import Foundation

struct Order {
    let id: String
}

struct OrderPrice {
    let price: Double
    static let formatter: NumberFormatter = {
        let formatter = NumberFormatter()
        formatter.locale = Locale.current
        formatter.numberStyle = .currency
        return formatter
    }()
   
    func prettyPrint() -> String {
        return OrderPrice.formatter.string(from: price as NSNumber) ?? "\(price)"
    }
}

struct PriceMultiplier {
    let value: Double
}

struct PriceDiscount {
    let value: Double
}
