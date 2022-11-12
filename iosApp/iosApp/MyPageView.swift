//
//  MyPageView.swift
//  iosApp
//
//  Created by whenyourapprun on 2022/11/12.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct MyPageView: View {
    @EnvironmentObject var userStore: UserStore
    // 다국어 처리
    private let MyPageOK: LocalizedStringKey = "OK"
    private let MyPageLoading: LocalizedStringKey = "Loading"
    private let MyPageMain: LocalizedStringKey = "Main"
    private let MyPageError: LocalizedStringKey = "Error"
    // 뷰 전환
    @State private var showNextView = false
    
    var body: some View {
        ZStack {
            Color.back.edgesIgnoringSafeArea(.all)
            HStack(spacing: 16.0) {
                VStack(spacing: 16.0) {
                    Spacer()
                    Text("MyPage View")
                    Spacer()
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
            
        })
        .fullScreenCover(isPresented: $showNextView) {
            MainView()
        }
    }
}

struct MyPageView_Previews: PreviewProvider {
    static var previews: some View {
        MyPageView()
    }
}
