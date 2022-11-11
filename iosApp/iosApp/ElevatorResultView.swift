//
//  ElevatorResultView.swift
//  iosApp
//
//  Created by whenyourapprun on 2022/11/11.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ElevatorResultView: View {
    @EnvironmentObject var userStore: UserStore
    @State private var text = "Loading"
    var body: some View {
        NavigationView {
            ZStack {
                Color.back.edgesIgnoringSafeArea(.all)
                Text(text)
            }
            .statusBarHidden(true)
            .navigationBarHidden(true)
            .onAppear(perform: {
                // 화면 나타날 때 자료 가져와서 표시하자.
                if let responseElevator = userStore.responseElevator {
                    text = responseElevator.response.header.resultMsg
                } else {
                    text = "error"
                }
            })
        }
    }
}

struct ElevatorResultView_Previews: PreviewProvider {
    static var previews: some View {
        ElevatorResultView().environmentObject(UserStore())
    }
}
