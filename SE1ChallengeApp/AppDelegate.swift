
import UIKit

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow()
        window?.rootViewController = SEChallengeViewController(vm: SEChallengeViewModelImpl(service: .init()))
        window?.makeKeyAndVisible()
        return true
    }
}

