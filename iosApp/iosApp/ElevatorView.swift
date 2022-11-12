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
    @EnvironmentObject var userStore: UserStore
    @State var elevatorNo: String = "8088381"
    @State private var showAlert = false
    // 다국어 처리
    private let ElevatorInputGuide: LocalizedStringKey = "ElevatorInputGuide"
    private let ElevatorInputPlace: LocalizedStringKey = "ElebatorInputPlace"
    private let ElavatorOK: LocalizedStringKey = "OK"
    private let ElevatorReceivedData: LocalizedStringKey = "ReceivedData"
    private let ElevatorReceivedDetail: LocalizedStringKey = "ReceivedDetail"
    private let ElevatorLoading: LocalizedStringKey = "Loading"
    // 뷰 전환
    @State private var showNextView = false
    
    var body: some View {
        ZStack {
            Color.back.edgesIgnoringSafeArea(.all)
            HStack(spacing: 16.0) {
                VStack(spacing: 16.0) {
                    Text(ElevatorInputGuide)
                        .padding()
                        .font(.body)
                        .foregroundColor(.not_selected)
                        .multilineTextAlignment(.center)
                        .minimumScaleFactor(0.5)
                        .lineLimit(1)
                    TextField(ElevatorInputPlace, text: $elevatorNo)
                        .keyboardType(.decimalPad)
                        .font(.largeTitle)
                        .foregroundColor(Color.text)
                        .multilineTextAlignment(.center)
                        .minimumScaleFactor(0.5)
                        .lineLimit(1)
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
                        Text(ElavatorOK)
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
                        title: Text(ElevatorReceivedData),
                        message: Text(ElevatorReceivedDetail),
                        dismissButton: .default(Text(ElavatorOK), action: { showNextView = true })
                    )
                }
            }
            .padding(16.0)
        }
        .statusBarHidden(true)
        .fullScreenCover(isPresented: $showNextView) {
            ElevatorResultView()
        }
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
