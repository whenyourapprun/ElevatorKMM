import SwiftUI
import shared

struct IntroView: View {
    // 오프닝 광고
    @ObservedObject var appOpen = AppOpen()
    // 타이머
    @State private var count: Double = 0.0
    @State var timer = Timer.publish(every: 0.1, on: .main, in: .common).autoconnect()
    // 다음 화면 이동
    @State private var showMainScreen = false
    // 다국어 처리
    private let elevator: LocalizedStringKey = "Elevator"
    
	var body: some View {
        ZStack {
            Color.back.edgesIgnoringSafeArea(.all)
            HStack(spacing: 16.0) {
                VStack(spacing: 16.0) {
                    Text(elevator)
                        .font(.largeTitle)
                        .fontWeight(.bold)
                    ProgressView(value: count, total: 100)
                        .progressViewStyle(LinearProgressViewStyle(tint: .selected))
                        .background(Color.not_selected)
                        .scaleEffect(x: 1, y: 4, anchor: .center)
                        .padding()
                }
                .padding(16.0)
                .background(Color.white)
                .cornerRadius(8.0)
                .onReceive(timer) { _ in
                    if count < 100 {
                        count += 1
                    } else {
                        // 타이머 종료
                        self.timer.upstream.connect().cancel()
                        // 메인 화면 이동
                        showMainScreen = true
                    }
                }
            }
            .padding(16.0)
        }
        .statusBarHidden(true)
        .onReceive(NotificationCenter.default.publisher(for: .openAdLoad), perform: { _ in
            // 광고 로딩
            if appOpen.appOpenAdLoaded {
                // 타이머 종료
                self.timer.upstream.connect().cancel()
                // 광고 로딩
                appOpen.ShowAppOpenAd()
            }
        })
        .onReceive(NotificationCenter.default.publisher(for: .openAdDidDismissFullScreenContent)) { _ in
            // 오프닝광고 끝났을 때 메인창으로 이동, 여기서 메인창으로 나와야 하는데 안나옴
            print("IntroView openAdDidDismissFullScreenContent")
            self.timer = Timer.publish(every: 0.1, on: .main, in: .common).autoconnect()
        }
        .fullScreenCover(isPresented: $showMainScreen) {
            MainView()
        }
	}
}

struct IntroView_Previews: PreviewProvider {
	static var previews: some View {
        IntroView()
            .environment(\.locale, .init(identifier: "ko"))
	}
}
