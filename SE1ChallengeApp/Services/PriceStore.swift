
import Foundation

protocol PriceStore {
    func loadPrice(order: Order, completion: @escaping (Result<OrderPrice, Error>) -> Void)
    func savePrice(order: Order, orderPrice: OrderPrice)
}

enum PriceStoreError: Error {
    case noData(String)
}

final class PriceStoreImpl: PriceStore {
    private let persistentDataStore: PersistentDataStore
    
    init(persistentDataStore: PersistentDataStore) {
        self.persistentDataStore = persistentDataStore
    }
    
    func loadPrice(order: Order, completion: @escaping (Result<OrderPrice, Error>) -> Void) {
        performOnBackground { [weak self] in
            guard let self = self else { return }
            if let data = self.persistentDataStore.getOrderPriceFromCache(orderId: order.id) {
                completion(.success(OrderPrice(price: data)))
            } else {
                completion(.failure(self.getOrderPriceError()))
            }
        }
    }
    
    func savePrice(order: Order, orderPrice: OrderPrice) {
        persistentDataStore.orderPrice[order.id] = orderPrice.price
    }
    
    private func performOnBackground(action: @escaping () -> Void) {
        DispatchQueue.global(qos: .background).async(execute: action)
    }
    private func getOrderPriceError() -> Error { PriceStoreError.noData(":(") }
}
