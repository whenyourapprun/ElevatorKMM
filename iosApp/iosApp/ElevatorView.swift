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
    // 전역 벼수
    @EnvironmentObject var userStore: UserStore
    // 광고
    @State var showRewardedAd: Bool = false
    // 변수
    @State var elevatorNo: String = ""//"8088381"
    @State private var checkButton = false
    @State private var isLoading = false
    @State private var showAlert = false
    // 다국어 처리
    private let ElevatorInputGuide: LocalizedStringKey = "ElevatorInputGuide"
    private let ElevatorInputWait: LocalizedStringKey = "ElevatorInputWait"
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
                    // 상태 안내창 문구
                    Text(checkButton ? ElevatorInputGuide : ElevatorInputWait)
                        .padding()
                        .font(.body)
                        .foregroundColor(.not_selected)
                        .multilineTextAlignment(.center)
                        .minimumScaleFactor(0.5)
                        .lineLimit(1)
                    // 엘리베이터 번호 입력
                    TextField("8088-381", text: $elevatorNo)
                        .onChange(of: elevatorNo, perform: { newValue in
                            // 4자리 입력하면 자동으로 - 추가
                            if elevatorNo.count == 0 {
                                checkButton = false
                            } else if elevatorNo.count == 1 {
                                checkButton = true
                            } else if elevatorNo.count == 4 {
                                elevatorNo += "-"
                            } else if elevatorNo.count > 8 {
                                // 자리수에 넘게 입력하면 초기화
                                elevatorNo = ""
                            }
                        })
                        .keyboardType(.decimalPad)
                        .font(.largeTitle)
                        .foregroundColor(Color.text)
                        .multilineTextAlignment(.center)
                        .minimumScaleFactor(0.5)
                        .lineLimit(1)
                    // 하단의 확인버튼
                    Button(action: {
                        if checkButton == true {
                            // 터치하면 버튼 비활성
                            checkButton = false
                            // 로딩 상태 표시
                            isLoading = true
                            // 입력된 번호(- 빼고 전달)로 엘리베이터 점검 결과를 가져온다.
                            let sendElevatorNo = elevatorNo.replacingOccurrences(of: "-", with: "")
                            print("sendElevatorNo \(sendElevatorNo)")
                            Elevator().getElevatorInfo(elevatorNo: sendElevatorNo, completionHandler: { response, error in
                                DispatchQueue.main.async {
                                    // 로딩 창을 감추자
                                    isLoading = false
                                    if let elevatorInfo = response {
                                        userStore.responseElevator = elevatorInfo
                                    }
                                }
                            })
                        }
                        // 키보드 감추기
                        hideKeyboard()
                    }, label: {
                        Text(ElavatorOK)
                            .padding()
                            .frame(maxWidth: .infinity)
                            .background(checkButton ? Color.selected : Color.not_selected)
                            .cornerRadius(8.0)
                            .font(.largeTitle)
                            .foregroundColor(checkButton ? .white : .text)
                    })
                    .disabled(checkButton == false)
                }
                .padding(16.0)
                .background(Color.white)
                .cornerRadius(8.0)
                .alert(isPresented: $showAlert) {
                    // Alert 띄우고 버튼 클릭하면 결과 창으로 이동
                    Alert(
                        title: Text(ElevatorReceivedData),
                        message: Text(ElevatorReceivedDetail),
                        dismissButton:
                                .default(Text(ElavatorOK),
                                                action: {
                                                    // 리워드 전면광고 띄우기
                                                    showRewardedAd = true
                                                }
                                        )
                    )
                }
            }
            .padding(16.0)
            .sheet(isPresented: $isLoading, content: {
                // 로딩 창을 띄운다
                ProgressView {
                    Text(ElevatorLoading)
                        .font(.largeTitle)
                        .fontWeight(.bold)
                }
                .progressViewStyle(CircularProgressViewStyle(tint: .text))
                .frame(maxWidth: .infinity, maxHeight: 200, alignment: .center)
                .background(Color.white)
                .cornerRadius(16.0)
                .padding()
                .onDisappear {
                    // Alert 로 결과를 띄워준다.
                    showAlert = true
                }
            })
        }
        .statusBarHidden(true)
        .presentRewardedAd(isPresented: $showRewardedAd, adUnitId: rewardId) {
            print("Reward Granted")
        }
        .onReceive(NotificationCenter.default.publisher(for: .rewardedAdDidDismissFullScreenContent)) { _ in
            // 전면광고 끝났을 때 결과 창으로
            showNextView = true
        }
        .fullScreenCover(isPresented: $showNextView) {
            ElevatorResultView()
        }
    }
}

extension ElevatorView {
    func hideKeyboard() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

struct Elevator_Previews: PreviewProvider {
    static var previews: some View {
        ElevatorView().environment(\.locale, .init(identifier: "ko"))
    }
}
