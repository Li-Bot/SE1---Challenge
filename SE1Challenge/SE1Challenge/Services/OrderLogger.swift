//
//  OrderLogger.swift
//  SE1Challenge
//
//  Created by Michal Miko on 19.08.2021.
//

import Foundation

protocol Analytics {
    static func log(order: Order, price: OrderPrice)
}

final class Logger: Analytics {
    static func log(order: Order, price: OrderPrice) {
        print("Order: \(order.id). Price: \(price.price)")
    }
}
