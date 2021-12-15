
import UIKit

typealias VMType = ViewModel

class Controller<VMType>: UIViewController {
    var vm: VMType
    
    required init(vm: VMType) {
        self.vm = vm
        super.init(nibName: String(describing: Self.self), bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setup()
    }
    
    func setup() {
        view.backgroundColor = .white
    }
    
    deinit {
        print(String(describing: type(of: self)) + " deinit")
    }
}
