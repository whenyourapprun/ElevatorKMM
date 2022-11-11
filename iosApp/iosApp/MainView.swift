//
//  MainView.swift
//  iosApp
//
//  Created by whenyourapprun on 2022/10/12.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MainView: View {
    // 하단 버튼 애니메이션과 선택 표시를 위한 저장가능한 변수
    @State private var isAni_1 = false
    @State private var select_1 = true
    @State private var isAni_2 = false
    @State private var select_2 = false
    @State private var isAni_3 = false
    @State private var select_3 = false
    // 애니메이션 설정변수
    var ani: Animation { Animation.linear(duration: 0.1) }
    // 다국어 처리
    private let Elevator: LocalizedStringKey = "Elevator"
    private let ElevatorGuide: LocalizedStringKey = "ElevatorGuide"
    private let MyPage: LocalizedStringKey = "MyPage"
    private let MyPageGuide: LocalizedStringKey = "MyPageGuide"
    private let NickChange: LocalizedStringKey = "NickChange"
    private let NickChangeGuide: LocalizedStringKey = "NickChangeGuide"
    
    var body: some View {
        NavigationView {
            VStack {
                CardView(title: Elevator, guide: ElevatorGuide, nextIndex: 0)
                CardView(title: MyPage, guide: MyPageGuide, nextIndex: 1)
                CardView(title: NickChange, guide: NickChangeGuide, nextIndex: 2)
                Spacer()
                HStack {
                    Text("1")
                        .font(.title)
                        .foregroundColor(select_1 ? .white : .text)
                        .frame(maxWidth: .infinity, maxHeight: 70.0)
                        .background(select_1 ? Color.selected : Color.not_selected)
                        .cornerRadius(5.0)
                        .scaleEffect(isAni_1 ? 0.9 : 1)
                        .animation(ani)
                        .onTapGesture {
                            isAni_1 = true
                            select_1 = true
                            select_2 = false
                            select_3 = false
                            DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(100)) {
                                isAni_1 = false
                            }
                        }
                    Text("2")
                        .font(.title)
                        .foregroundColor(select_2 ? .white : .text)
                        .frame(maxWidth: .infinity, maxHeight: 70.0)
                        .background(select_2 ? Color.selected : Color.not_selected)
                        .cornerRadius(5.0)
                        .scaleEffect(isAni_2 ? 0.9 : 1)
                        .animation(ani)
                        .onTapGesture {
                            isAni_2 = true
                            select_1 = false
                            select_2 = true
                            select_3 = false
                            DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(100)) {
                                isAni_2 = false
                            }
                        }
                    Text("3")
                        .font(.title)
                        .foregroundColor(select_3 ? .white : .text)
                        .frame(maxWidth: .infinity, maxHeight: 70.0)
                        .background(select_3 ? Color.selected : Color.not_selected)
                        .cornerRadius(5.0)
                        .scaleEffect(isAni_3 ? 0.9 : 1)
                        .animation(ani)
                        .onTapGesture {
                            isAni_3 = true
                            select_1 = false
                            select_2 = false
                            select_3 = true
                            DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(100)) {
                                isAni_3 = false
                            }
                        }
                }
                .padding([.horizontal], 16.0)
            }
            .background(Color.back.ignoresSafeArea(.all))
            .statusBarHidden(true)
            .navigationBarHidden(true)
        }
    }
}

struct CardView: View {
    // 제목과 설명
    var title: LocalizedStringKey
    var guide: LocalizedStringKey
    // 전환 화면 0 Elevator, 1 MyPage, 2 Nick, 나머지 MainView
    var nextIndex: Int
    // 애니메이션
    @State private var isAnimated = false
    var ani: Animation { Animation.linear(duration: 0.2) }
    // 뷰 전환
    @State private var showNextView = false
    
    var body: some View {
        ZStack {
            Color.back.edgesIgnoringSafeArea(.all)
            VStack {
                HStack {
                    Spacer()
                    VStack {
                        Text(title)
                            .font(.largeTitle)
                            .fontWeight(.bold)
                            .foregroundColor(.text)
                            .padding()
                        Text(guide)
                            .font(.body)
                            .foregroundColor(.selected)
                            .padding()
                    }
                    Spacer()
                }
                .padding()
            }
            .background(Color.white)
            .cornerRadius(15.0)
            .padding([.all], 16.0)
            .scaleEffect(isAnimated ? 0.9 : 1)
            .animation(ani)
            .onTapGesture {
                isAnimated = true
                DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(100)) {
                    isAnimated = false
                    if nextIndex == 0 {
                        showNextView = true
                    }
                }
            }
        }
        .statusBarHidden(true)
        .navigationBarHidden(true)
        .fullScreenCover(isPresented: $showNextView) {
            if nextIndex == 0 {
                ElevatorView()
            }
        }
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
            .environment(\.locale, .init(identifier: "ko"))
    }
}
