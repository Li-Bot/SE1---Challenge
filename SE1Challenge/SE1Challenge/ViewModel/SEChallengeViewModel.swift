//
//  SEChallengeViewModel.swift
//  SE1Challenge
//
//  Created by Michal Miko on 18.08.2021.
//

import Foundation

enum GenerateResult {
    case cacheLoaded(OrderPrice)
    case priceGenerated(OrderPrice)
    case priceFailed
}

protocol Orderable {
    func generate(completion: @escaping (GenerateResult) -> Void)
}

final class SEChallengeViewModel: Orderable {
    internal init(pricingLoader: OrderPricingLoader,
                  orderGenerator: OrderGenerator,
                  cache: InMemoryCachable,
                  multiplierHandler: PriceMultiplierHandler,
                  discountHandler: PriceDiscountHandler) {
        self.pricingLoader = pricingLoader
        self.orderGenerator = orderGenerator
        self.cache = cache
        self.multiplierHandler = multiplierHandler
        self.discountHandler = discountHandler
    }
    
    private let pricingLoader: OrderPricingLoader
    private let orderGenerator: OrderGenerator
    private let cache: InMemoryCachable
    private var multiplierHandler: PriceMultiplierHandler
    private var discountHandler: PriceDiscountHandler
    private var defaultNumberOfAttemtsForPriceLoad: Int = 2
    private var remainingAttemtsForPriceLoad: Int = 0
    private var resultAction: ((GenerateResult) -> Void)?
    
    func generate(completion: @escaping (GenerateResult) -> Void) {
        self.resultAction = completion
        resetState()
        let order = orderGenerator.next()
        checkCache(for: order)
    }
    
    func resetState() {
        remainingAttemtsForPriceLoad = defaultNumberOfAttemtsForPriceLoad
    }
    
    private func checkCache(for order: Order) {
        cache.loadPrice(for: order) { result in
            switch result {
            case .success(let price):
                publishResult(result: .cacheLoaded(price))
            case .failure(_):
                self.generatePrice(for: order)
            }
        }
    }
    
    private func generatePrice(for order: Order) {
        guard remainingAttemtsForPriceLoad > 0 else {
            publishResult(result: .priceFailed)
            return
        }
        remainingAttemtsForPriceLoad -= 1
        pricingLoader.load(order: order) { result in
            switch result {
            case .success(let price):
                self.proccessPriceAndPublish(order: order, price: price)
            case .failure(_):
                self.generatePrice(for: order)
            }
        }
    }
    
    private func proccessPriceAndPublish(order: Order, price: OrderPrice) {
        let multipledPrice = multiplierHandler.applyMultiplier(to: price)
        let discountedPrice = discountHandler.applyDiscount(to: multipledPrice)
        cache.saveOrderPrice(order: order, price: discountedPrice)
        Logger.log(order: order, price: discountedPrice)
        publishResult(result: .priceGenerated(discountedPrice))
    }
    
    private func publishResult(result: GenerateResult) {
        DispatchQueue.main.async {
            self.resultAction?(result)
        }
    }
    
}
