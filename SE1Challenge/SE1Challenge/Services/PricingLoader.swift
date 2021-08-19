//
//  PricingLoader.swift
//  SE1Challenge
//
//  Created by Michal Miko on 18.08.2021.
//

import Foundation

protocol OrderPricingLoader {
    func load(order: Order, completion: @escaping (Result<OrderPrice, Error>) -> Void)
}

enum OrderPricingLoaderError: Error {
    case generic(String)
}

final class RandomOrderPricingLoader: OrderPricingLoader {
    func load(order: Order, completion: @escaping (Result<OrderPrice, Error>) -> Void) {
        performOnBackground { [weak self] in
            guard let self = self else { return }
            if (self.isFailure()) {
                completion(.failure(self.getPricingError()))
            } else {
                completion(.success(self.getRandomOrderPrice()))
            }
        }
    }

    private func performOnBackground(action: @escaping () -> Void) {
        DispatchQueue.global(qos: .background).async(execute: action)
    }
    private func isFailure() -> Bool { Bool.random() }
    private func getPricingError() -> Error { OrderPricingLoaderError.generic(":(") }
    private func getRandomOrderPrice() -> OrderPrice { OrderPrice(price: getRandomPrice()) }
    private func getRandomPrice() -> Double { Double.random(in: 1000.0 ..< 200000.0) }
}
