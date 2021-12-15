
import Foundation

protocol ServiceManager {
    func createRandomOrderPricingLoader() -> OrderPricingLoader
    func createPriceDataManager() -> PriceStore
    func createRandomOrderPriceCalculator() -> OrderPriceGenerator
    func createPersistentDataStore() -> PersistentDataStore
    func createOrderGenerator() -> OrderGenerator
    func createLogger() -> SELogger
}

class ServiceManagerImpl: ServiceManager {
    func createRandomOrderPricingLoader() -> OrderPricingLoader {
        RandomOrderPricingLoader()
    }
    func createPriceDataManager() -> PriceStore {
        PriceStoreImpl(persistentDataStore: createPersistentDataStore())
    }
    func createRandomOrderPriceCalculator() -> OrderPriceGenerator {
        OrderPriceGeneratorImpl()
    }
    func createPersistentDataStore() -> PersistentDataStore {
        PersistentDataStore()
    }
    func createOrderGenerator() -> OrderGenerator {
        return RandomOrderGenerator(probability: 0.5)
    }
    func createLogger() -> SELogger {
        return OrderPriceLogger()
    }
}
