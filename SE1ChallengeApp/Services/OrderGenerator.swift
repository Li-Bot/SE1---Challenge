
import Foundation

protocol OrderGenerator {
    func next() -> Order
}

final class RandomOrderGenerator: OrderGenerator {
    private var last: Order!
    private let probability: Double
    
    init(probability: Double) {
        self.probability = probability
        last = createNewOrder()
    }
    
    func next() -> Order {
        if shouldCreateNewOrder() {
            last = createNewOrder()
        }
        return last
    }
    
    private func createNewOrder() -> Order { Order(id: UUID().uuidString) }
    private func shouldCreateNewOrder() -> Bool { getRandomProbability() < probability }
    private func getRandomProbability() -> Double { Double.random(in: 0.0 ... 1.0) }
}
