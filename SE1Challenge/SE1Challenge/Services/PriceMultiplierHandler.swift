//
//  PriceMultiplier.swift
//  SE1Challenge
//
//  Created by Michal Miko on 19.08.2021.
//

import Foundation

protocol PriceMultiplierHandler {
    func applyMultiplier(to price: OrderPrice) -> OrderPrice
}

struct PriceHandlerImplementation: PriceMultiplierHandler {
    let multiplier: PriceMultiplier
    
    func applyMultiplier(to price: OrderPrice) -> OrderPrice {
        let newPrice = price.price * multiplier.value
        return .init(price: newPrice)
    }
}
