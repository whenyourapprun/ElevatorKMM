import SwiftUI

@main
struct iOSApp: App {
    
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
    }
    
	var body: some Scene {
		WindowGroup {
            // 앱 전체에서 사용할 수 있도록 여기에 추가
            IntroView().environmentObject(UserStore())
		}
	}
}
