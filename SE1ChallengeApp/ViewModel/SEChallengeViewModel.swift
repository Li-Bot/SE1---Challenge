
import Foundation

protocol SEChallengeViewModel: ViewModel {
    func runButtonTapped(completion: @escaping (Result<OrderPrice, Error>) -> Void)
}

class SEChallengeViewModelImpl {
    private let orderPricingLoader: OrderPricingLoader
    private let dataBaseManager: PriceStore
    private let orderPriceCalculator: OrderPriceGenerator
    private let orderGenerator: OrderGenerator
    private let logger: SELogger
    
    private var completion: ((Result<OrderPrice, Error>) -> Void)?
    
    init(service: ServiceManagerImpl) {
        orderPricingLoader = service.createRandomOrderPricingLoader()
        dataBaseManager = service.createPriceDataManager()
        orderPriceCalculator = service.createRandomOrderPriceCalculator()
        orderGenerator = service.createOrderGenerator()
        logger = service.createLogger()
    }
        
    private func fetchOrder() {
        let order = orderGenerator.next()
        dataBaseManager.loadPrice(order: order) { [weak self] result in
            guard let self = self else { return }
            switch result {
            case .success(let orderPrice):
                self.calculatorPrice(order: order, orderPrice: orderPrice, isNew: false)
            case .failure:
                self.fetchPriceFromService(order: order, shouldRetry: true)
            }
            
        }
    }
    
    private func fetchPriceFromService(order: Order, shouldRetry: Bool) {
        orderPricingLoader.load(order: order) { [weak self] result in
            switch result {
            case .success(let orderPrice):
                self?.dataBaseManager.savePrice(order: order, orderPrice: orderPrice)
                self?.calculatorPrice(order: order, orderPrice: orderPrice, isNew: true)
            case .failure(let error):
                if !shouldRetry {
                    self?.fetchComplete(result: .failure(error))
                } else {
                    self?.fetchPriceFromService(order: order, shouldRetry: false)
                }
            }
        }
    }
    
    private func calculatorPrice(order: Order, orderPrice: OrderPrice, isNew: Bool) {
        logIfNeed(order: order, orderPrice: orderPrice, isNew: isNew)
        let orderPriceMultiple = orderPriceCalculator.multiplePrice(orderPrice: orderPrice)
        logIfNeed(order: order, orderPrice: orderPriceMultiple, isNew: isNew)
        let orderPriceDiscount = orderPriceCalculator.discountPrice(orderPrice: orderPriceMultiple)
        logIfNeed(order: order, orderPrice: orderPriceDiscount, isNew: isNew)
        
        fetchComplete(result: .success(orderPriceDiscount))
    }
    
    private func logIfNeed(order: Order, orderPrice: OrderPrice, isNew: Bool) {
        if isNew {
            logger.log(order: order, price: orderPrice)
        }
    }
    
    private func fetchComplete(result: Result<OrderPrice, Error>) {
        DispatchQueue.main.async { [weak self] in
            self?.completion?(result)
        }
    }
}

/// Output
extension SEChallengeViewModelImpl: SEChallengeViewModel {
    func runButtonTapped(completion: @escaping (Result<OrderPrice, Error>) -> Void) {
        self.completion = completion
        fetchOrder()
    }
}
