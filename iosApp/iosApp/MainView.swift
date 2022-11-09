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
    @ObservedObject private(set) var viewModel: ViewModel
    var body: some View {
        Text(viewModel.text)
    }
}

extension MainView {
    class ViewModel: ObservableObject {
        @Published var text = "Loading..."
        init() {
            Elevator().getElevatorInfo(elevatorNo: "8088381", completionHandler: { response, error in
                DispatchQueue.main.async {
                    if let elevatorInfo = response {
                        self.text = elevatorInfo.response.header.resultMsg
                    } else {
                        self.text = error?.localizedDescription ?? "error"
                    }
                }
            })
        }
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView(viewModel: MainView.ViewModel())
    }
}
