import SwiftUI
import shared

struct IntroView: View {
	let greet = Greeting().greeting()
    @State private var count: Double = 0.0
    let timer = Timer.publish(every: 0.1, on: .main, in: .common).autoconnect()
    @State private var showMainScreen = false
    
	var body: some View {
		//Text(greet)
        ZStack {
            Color.gray.edgesIgnoringSafeArea(.all)
            HStack(spacing: 16.0) {
                VStack(spacing: 16.0) {
                    Text("Elevator")
                    ProgressView(value: count, total: 100)
                        .progressViewStyle(LinearProgressViewStyle(tint: .gray))
                }
                .padding(16.0)
                .background(Color.white)
                .onReceive(timer) { _ in
                    if count < 100 {
                        count += 5
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
	}
}

struct IntroView_Previews: PreviewProvider {
	static var previews: some View {
		IntroView()
	}
}
