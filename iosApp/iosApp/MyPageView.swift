//
//  MyPageView.swift
//  iosApp
//
//  Created by whenyourapprun on 2022/11/12.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct MyPageView: View {
    // 다국어 처리
    private let MyPageTitle: LocalizedStringKey = "MyPage"
    private let MyPageNickChange: LocalizedStringKey = "NickChange"
    private let MyPageNickSample: LocalizedStringKey = "NickSample"
    private let MyPageOK: LocalizedStringKey = "OK"
    private let MyPageLoading: LocalizedStringKey = "Loading"
    private let MyPageMain: LocalizedStringKey = "Main"
    private let MyPageError: LocalizedStringKey = "Error"
    // 뷰 전환
    @State private var showNextView = false
    @State private var showNickView = false
    @State private var nick: String? = UserDefaults.standard.string(forKey: "nick")
    @State private var itemList = [ELEVATOR_ITEM]()
    
    var body: some View {
        ZStack {
            Color.back.edgesIgnoringSafeArea(.all)
            HStack(spacing: 16.0) {
                VStack(spacing: 16.0) {
                    Text(MyPageTitle)
                        .font(.largeTitle)
                        .fontWeight(.bold)
                        .minimumScaleFactor(0.5)
                        .lineLimit(1)
                        .foregroundColor(.text)
                        .padding([.vertical])
                        .frame(maxWidth: .infinity, alignment: .center)
                        .background(Color.white)
                        .cornerRadius(16.0)
                        .padding([.horizontal])
                    
                    HStack {
                        Button {
                            showNickView = true
                        } label: {
                            Text(MyPageNickChange)
                                .font(.title)
                                .minimumScaleFactor(0.5)
                                .lineLimit(1)
                                .foregroundColor(.white)
                                .frame(maxWidth: UIScreen.main.bounds.width * 0.3, maxHeight: 70.0)
                                .background(Color.selected)
                        }
                        Text(nick ?? MyPageNickSample.stringValue())
                            .font(.body)
                            .minimumScaleFactor(0.5)
                            .lineLimit(1)
                            .foregroundColor(.text)
                            .frame(maxWidth: .infinity, maxHeight: 70.0, alignment: .center)
                            .background(Color.white)
                    }
                    .padding([.horizontal])
                    
                    List {
                        ForEach(itemList, id: \.date) { item in
                            HStack {
                                Text(item.number)
                                    .font(.body)
                                    .minimumScaleFactor(0.5)
                                    .lineLimit(1)
                                    .foregroundColor(.text)
                                    .padding([.vertical])
                                    .frame(maxWidth: .infinity, alignment: .center)
                                Divider()
                                Text(item.build)
                                    .font(.body)
                                    .minimumScaleFactor(0.5)
                                    .lineLimit(2)
                                    .foregroundColor(.text)
                                    .padding([.vertical])
                                    .frame(maxWidth: .infinity, alignment: .center)
                                Divider()
                                Text(item.date)
                                    .font(.body)
                                    .minimumScaleFactor(0.5)
                                    .lineLimit(1)
                                    .foregroundColor(.text)
                                    .padding([.vertical])
                                    .frame(maxWidth: .infinity, alignment: .center)
                            }
                        }
                    }
                    .listStyle(.automatic)
                    
                    Text(MyPageMain)
                        .font(.largeTitle)
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity, maxHeight: 90.0)
                        .background(Color.selected)
                        .cornerRadius(16.0)
                        .padding([.horizontal])
                        .onTapGesture {
                            showNextView = true
                        }
                }
            }
        }
        .statusBarHidden(true)
        .onAppear(perform: {
            // 화면 나타날 때 자료 가져와서 표시하자.
            if nick == nil {
                nick = MyPageNickSample.stringValue()
            }
            // itemList 초기화 및 내부 디비에서 자료 가져와서 표시하기
            itemList.removeAll()
            itemList = DBHelper().getHistory()
        })
        .fullScreenCover(isPresented: $showNextView) {
            MainView()
        }
        .fullScreenCover(isPresented: $showNickView) {
            NickView()
        }
    }
}

struct MyPageView_Previews: PreviewProvider {
    static var previews: some View {
        MyPageView().environment(\.locale, .init(identifier: "ko"))
    }
}
