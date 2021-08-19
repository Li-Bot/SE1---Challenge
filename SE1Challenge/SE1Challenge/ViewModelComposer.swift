//
//  ViewModelComposer.swift
//  SE1Challenge
//
//  Created by Michal Miko on 19.08.2021.
//

import Foundation

struct ViewModelComposer {
    func getViewModel() -> SEChallengeViewModel {
        return .init(pricingLoader: RandomOrderPricingLoader(),
                     orderGenerator: RandomOrderGenerator(probability: 0.5),
                     cache: InMemoryCache(),
                     multiplierHandler: PriceHandlerImplementation(multiplier: .init(value: 1.1)),
                     discountHandler: PriceDiscountImplementation())
    }
}
