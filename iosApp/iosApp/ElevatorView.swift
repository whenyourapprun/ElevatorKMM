//
//  Elevator.swift
//  iosApp
//
//  Created by whenyourapprun on 2022/11/09.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ElevatorView: View {
    @State var elevatorNo: String = "8088381"
    @State private var showAlert = false
    @EnvironmentObject var userStore: UserStore
    // 뷰 전환
    @State private var showNextView = false
    
    var body: some View {
        NavigationView {
            ZStack {
                Color.back.edgesIgnoringSafeArea(.all)
                HStack(spacing: 16.0) {
                    VStack(spacing: 16.0) {
                        Text("엘리베이터 번호를 입력하여 정보를 확인하세요.")
                            .padding()
                            .font(.body)
                            .foregroundColor(.not_selected)
                        TextField("Enter elevator number", text: $elevatorNo)
                            .keyboardType(.decimalPad)
                            .font(.largeTitle)
                            .foregroundColor(Color.text)
                            .multilineTextAlignment(.center)
                        Button(action: {
                            // 입력된 번호로 엘리베이터 점검 결과를 가져온다.
                            Elevator().getElevatorInfo(elevatorNo: elevatorNo, completionHandler: { response, error in
                                DispatchQueue.main.async {
                                    // Alert 로 결과를 띄워준다.
                                    showAlert = true
                                    if let elevatorInfo = response {
                                        userStore.responseElevator = elevatorInfo
                                    }
                                }
                            })
                            hideKeyboard()
                        }, label: {
                            Text("확인")
                                .padding()
                                .frame(maxWidth: .infinity)
                                .background(Color.selected)
                                .cornerRadius(8.0)
                                .font(.largeTitle)
                                .foregroundColor(.white)
                        })
                    }
                    .padding(16.0)
                    .background(Color.white)
                    .cornerRadius(8.0)
                    .alert(isPresented: $showAlert) {
                        // Alert 띄우고 버튼 클릭하면 결과 창으로 이동
                        Alert(
                            title: Text("받은 데이터"),
                            message: Text((userStore.responseElevator?.response.header.resultMsg)!),
                            dismissButton: .default(Text("확인"), action: { showNextView = true })
                        )
                    }
                }
                .padding(16.0)
            }
            .statusBarHidden(true)
            .navigationBarHidden(true)
            .fullScreenCover(isPresented: $showNextView) {
                ElevatorResultView()
            }
        }
    }
}

extension ElevatorView {
    class ViewModel: ObservableObject {
        @Published var text = "Loading..."
        @State private var elevatorNo: String = "8088381"
    }
}

#if canImport(UIKit)
extension View {
    func hideKeyboard() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}
#endif

struct Elevator_Previews: PreviewProvider {
    static var previews: some View {
        ElevatorView().environment(\.locale, .init(identifier: "ko"))
    }
}
