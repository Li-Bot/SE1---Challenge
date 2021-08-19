//
//  PriceDiscountHandler.swift
//  SE1Challenge
//
//  Created by Michal Miko on 19.08.2021.
//

import Foundation

protocol PriceDiscountHandler {
    func applyDiscount(to price: OrderPrice) -> OrderPrice
}

struct PriceDiscountImplementation: PriceDiscountHandler {
    func applyDiscount(to price: OrderPrice) -> OrderPrice {
        switch price.price {
        case let price where price > 150000:
            return .init(price: price * 0.5)
        case let price where price > 100000:
            return .init(price: price * 0.5)
        default:
            return price
        }
    }
}
