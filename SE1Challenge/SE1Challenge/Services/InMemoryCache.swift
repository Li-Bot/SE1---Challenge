//
//  InMemoryCache.swift
//  SE1Challenge
//
//  Created by Michal Miko on 18.08.2021.
//

import Foundation

enum MemoryError: Error {
    case itemNotFound
}

protocol InMemoryCachable {
    func saveOrderPrice(order: Order, price: OrderPrice)
    func loadPrice(for order: Order, completion: (Result<OrderPrice, MemoryError>)->())
}

struct InMemoryCache: InMemoryCachable {
    var cache: UserDefaults = UserDefaults.standard
    
    func saveOrderPrice(order: Order, price: OrderPrice) {
        cache.setValue(price.price, forKey: order.id)
    }
    
    func loadPrice(for order: Order, completion: (Result<OrderPrice, MemoryError>)->()) {
        guard let price = cache.object(forKey: order.id) as? Double else {
            completion(.failure(.itemNotFound))
            return
        }
        completion(.success(.init(price: price)))
    }
}
