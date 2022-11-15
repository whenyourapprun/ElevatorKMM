//
//  NickView.swift
//  iosApp
//
//  Created by whenyourapprun on 2022/11/12.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct NickView: View {
    // 광고
    @State var showRewardedAd: Bool = false
    // 지역 변수
    @State private var nick = ""
    @State private var checkButton = false
    // 다국어 처리
    private let NickChangeGuide: LocalizedStringKey = "NickChangeGuide"
    private let NickInput: LocalizedStringKey = "NickInput"
    // 뷰 전환
    @State private var showMainView = false
    
    var body: some View {
        ZStack {
            Color.back.edgesIgnoringSafeArea(.all)
            HStack(spacing: 16.0) {
                VStack(spacing: 16.0) {
                    // 상태 안내창 문구
                    Text(NickChangeGuide)
                        .padding()
                        .font(.body)
                        .foregroundColor(.not_selected)
                        .multilineTextAlignment(.center)
                        .minimumScaleFactor(0.5)
                        .lineLimit(1)
                    // 별명 입력
                    TextField(NickInput.stringValue(), text: $nick)
                        .onChange(of: nick, perform: { newValue in
                            if nick.count == 0 {
                                checkButton = false
                            } else {
                                checkButton = true
                            }
                        })
                        .keyboardType(.default)
                        .font(.largeTitle)
                        .foregroundColor(Color.text)
                        .multilineTextAlignment(.center)
                        .minimumScaleFactor(0.5)
                        .lineLimit(1)
                    // 하단의 확인버튼
                    Button(action: {
                        if checkButton == true {
                            // 입력된 별명 저장
                            UserDefaults.standard.set(nick, forKey: "nick")
                            // 터치하면 버튼 비활성
                            checkButton = false
                            // 리워드 전면광고 띄우기
                            showRewardedAd = true
                        }
                    }, label: {
                        Text("OK")
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
            }
            .padding(16.0)
        }
        .statusBarHidden(true)
        .onAppear {
            if let nick = UserDefaults.standard.string(forKey: "nick") {
                self.nick = nick
            }
        }
        .presentRewardedAd(isPresented: $showRewardedAd, adUnitId: rewardId) {
            print("Reward Granted")
        }
        .onReceive(NotificationCenter.default.publisher(for: .rewardedAdDidDismissFullScreenContent)) { _ in
            // 전면광고 끝났을 때 메인 창으로
            showMainView = true
        }
        .fullScreenCover(isPresented: $showMainView) {
            MainView()
        }
    }
}

struct NickView_Previews: PreviewProvider {
    static var previews: some View {
        NickView().environment(\.locale, .init(identifier: "ko"))
    }
}
