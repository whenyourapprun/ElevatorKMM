import SwiftUI

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
            // 앱 전체에서 사용할 수 있도록 여기에 추가
            IntroView().environmentObject(UserStore())
		}
	}
}
