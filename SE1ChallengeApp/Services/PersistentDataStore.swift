
import Foundation

struct UserDefaultKeys {
    static let orderPricesKey = "orderPricesKey"
}

class PersistentDataStore {
    private let userDefaults = UserDefaults.standard
    
    var orderPrice: [String: Double] {
        get {
            return userDefaults.value(forKey: UserDefaultKeys.orderPricesKey) as? [String: Double] ?? [:]
        }
        set {
            userDefaults.setValue(newValue, forKey: UserDefaultKeys.orderPricesKey)
        }
    }
    
    func getOrderPriceFromCache(orderId: String) -> Double? { orderPrice[orderId] }
}
