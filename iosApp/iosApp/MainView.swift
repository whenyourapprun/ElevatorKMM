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
    var body: some View {
        VStack {
            Text("엘리베이터")
            Text("퍼스널컬러")
            Text("MBTI")
            Text("마이페이지")
            Text("별명 수정")
        }
        .statusBarHidden(true)
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
