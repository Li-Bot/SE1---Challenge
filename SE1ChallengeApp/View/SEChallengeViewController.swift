
import UIKit

class SEChallengeViewController: Controller<SEChallengeViewModel> {
    
    @IBOutlet private var lbResult: UILabel!
    
    override func setup() {
        super.setup()
        vm.orderPriceChanged = { [weak self] orderText in
            DispatchQueue.main.async {
                self?.lbResult.text = orderText
            }
        }
    }
    
    @IBAction func actionRun(_ sender: Any) {
        vm.runButtonTapped()
    }
}
