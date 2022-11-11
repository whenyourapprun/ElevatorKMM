import SwiftUI
import shared

struct IntroView: View {
    @State private var count: Double = 0.0
    let timer = Timer.publish(every: 0.1, on: .main, in: .common).autoconnect()
    @State private var showMainScreen = false
    private let elevator: LocalizedStringKey = "Elevator"
	var body: some View {
        NavigationView {
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
                .fullScreenCover(isPresented: $showMainScreen) {
                    MainView()
                }
            }
            .statusBarHidden(true)
            .navigationBarHidden(true)
        }
	}
}

struct IntroView_Previews: PreviewProvider {
	static var previews: some View {
        IntroView()
            .environment(\.locale, .init(identifier: "ko"))
	}
}
