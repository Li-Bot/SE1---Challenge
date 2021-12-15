
import Foundation

protocol SEChallengeViewModel: ViewModel {
    func runButtonTapped() -> Void
    var orderPriceChanged: ((String) -> Void)? { get set }
}

class SEChallengeViewModelImpl {
    private let orderPricingLoader: OrderPricingLoader
    private let dataBaseManager: PriceStore
    private let orderPriceCalculator: OrderPriceGenerator
    private let orderGenerator: OrderGenerator
    private let logger: SELogger
    
    var _orderPriceChanged: ((String) -> Void)?
    
    init(service: ServiceManager) {
        orderPricingLoader = service.createRandomOrderPricingLoader()
        dataBaseManager = service.createPriceDataManager()
        orderPriceCalculator = service.createRandomOrderPriceCalculator()
        orderGenerator = service.createOrderGenerator()
        logger = service.createLogger()
    }
        
    private func fetchOrder() {
        let order = orderGenerator.next()
        _orderPriceChanged?("Loading...")
        dataBaseManager.loadPrice(order: order) { [weak self] result in
            guard let self = self else { return }
            switch result {
            case .success(let orderPrice):
                self.calculatePrice(order: order, orderPrice: orderPrice, isNew: false)
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
                self?.calculatePrice(order: order, orderPrice: orderPrice, isNew: true)
            case .failure(let error):
                if !shouldRetry {
                    self?.fetchComplete(result: .failure(error))
                } else {
                    self?.fetchPriceFromService(order: order, shouldRetry: false)
                }
            }
        }
    }
    
    private func calculatePrice(order: Order, orderPrice: OrderPrice, isNew: Bool) {
        logIfNeeded(order: order, orderPrice: orderPrice, isNew: isNew)
        let orderPriceMultiple = orderPriceCalculator.multiplePrice(orderPrice: orderPrice)
        logIfNeeded(order: order, orderPrice: orderPriceMultiple, isNew: isNew)
        let orderPriceDiscount = orderPriceCalculator.discountPrice(orderPrice: orderPriceMultiple)
        logIfNeeded(order: order, orderPrice: orderPriceDiscount, isNew: isNew)
        
        fetchComplete(result: .success(orderPriceDiscount))
    }
    
    private func logIfNeeded(order: Order, orderPrice: OrderPrice, isNew: Bool) {
        if isNew {
            logger.log(order: order, price: orderPrice)
        }
    }
    
    private func fetchComplete(result: Result<OrderPrice, Error>) {
        DispatchQueue.main.async { [weak self] in
            let text: String
            switch result {
            case .success(let orderPrice):
                text = "Load price success: \(orderPrice.prettyPrint())"
            case .failure:
                text = ":("
            }
            self?._orderPriceChanged?(text)
        }
    }
}

extension SEChallengeViewModelImpl: SEChallengeViewModel {
    /// Output
    var orderPriceChanged: ((String) -> Void)? {
        get {
            return _orderPriceChanged
        }
        set {
            _orderPriceChanged = newValue
        }
    }

    /// Input
    func runButtonTapped() {
        fetchOrder()
    }
}
