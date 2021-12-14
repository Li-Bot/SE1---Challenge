
import UIKit

class SEChallengeViewController: Controller<SEChallengeViewModel> {
    
    @IBOutlet private var lbResult: UILabel!
    
    override func setup() {
        super.setup()
    }
    
    @IBAction func actionRun(_ sender: Any) {
        lbResult.text = "Loading..."
        vm.runButtonTapped { [weak self] result in
            switch result {
            case .success(let orderPrice):
                self?.lbResult.text = "Load price success: \(orderPrice.price)"
            case .failure:
                self?.lbResult.text = ":("
            }
        }
    }
}
