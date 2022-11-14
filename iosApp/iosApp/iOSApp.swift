import SwiftUI
import GoogleMobileAds
import AppTrackingTransparency

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
            // 앱 전체에서 사용할 수 있도록 여기에 추가
            IntroView()
                .environmentObject(UserStore())
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.didBecomeActiveNotification)) { _ in
                    ATTrackingManager.requestTrackingAuthorization(completionHandler: { _ in })
                }
		}
	}
    
    init() {
        // 데이터베이스 생성은 한번만 해야 함.
        let uuid = UserDefaults.standard.string(forKey: "uuid")
        if uuid == nil {
            UserDefaults.standard.set(UUID().uuidString, forKey: "uuid")
            // 데이터베이스 생성해야 함.
            let db = DBHelper()
            print("createTable")
            db.createTable()
        }
        GADMobileAds.sharedInstance().start(completionHandler: nil)
        GADMobileAds.sharedInstance().requestConfiguration.testDeviceIdentifiers = [ GADSimulatorID, "1fbe08c670316f9a1fc08317899a857d" ]
        // DispatchQueue 이용
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            ATTrackingManager.requestTrackingAuthorization(completionHandler: { _ in })
        }
    }
}
