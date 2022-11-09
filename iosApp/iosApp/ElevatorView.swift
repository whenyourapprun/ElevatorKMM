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
    @ObservedObject private(set) var viewModel: ViewModel
    var body: some View {
        Text(viewModel.text)
    }
}

extension ElevatorView {
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

struct Elevator_Previews: PreviewProvider {
    static var previews: some View {
        ElevatorView(viewModel: ElevatorView.ViewModel())
    }
}
