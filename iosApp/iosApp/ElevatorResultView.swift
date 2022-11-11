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
    //@State private var items: [Item]?
    var body: some View {
        NavigationView {
            ZStack {
                Color.back.edgesIgnoringSafeArea(.all)
                VStack {
                    Text(text)
                    List {
                        if let itemList = userStore.responseElevator?.response.body.items {
                            ForEach(itemList, id: \.self) { item in
                                Section {
                                    Row(title: "address1", detail: item.address1)
                                    Row(title: "address2", detail: item.address2)
                                    Row(title: "applcBeDt", detail: item.applcBeDt)
                                    Row(title: "applcEnDt", detail: item.applcEnDt)
                                    Row(title: "areaNm", detail: item.areaNm)
                                    Row(title: "buldMgtNo1", detail: item.buldMgtNo1)
                                    Row(title: "buldMgtNo2", detail: item.buldMgtNo2)
                                    /*/Row(title: "buldNm", detail: item.buldNm)
                                    Row(title: "elevatorNo", detail: item.elevatorNo)
                                    Row(title: "elvtrAsignNo", detail: item.elvtrAsignNo)
                                    Row(title: "elvtrDetailForm", detail: item.elvtrDetailForm)
                                    Row(title: "elvtrDivNm", detail: item.elvtrDivNm)
                                    Row(title: "elvtrForm", detail: item.elvtrForm)
                                    Row(title: "elvtrKindNm", detail: item.elvtrKindNm)
                                    Row(title: "elvtrSttsNm", detail: item.elvtrSttsNm)
                                    Row(title: "frstInstallationDe", detail: item.frstInstallationDe)
                                    Row(title: "groundFloorCnt", detail: item.groundFloorCnt)
                                    Row(title: "installationDe", detail: item.installationDe)
                                    Row(title: "installationPlace", detail: item.installationPlace)
                                    Row(title: "liveLoad", detail: item.liveLoad)
                                    Row(title: "ratedCap", detail: item.ratedCap)
                                    Row(title: "resultNm", detail: item.resultNm)
                                    Row(title: "shuttleFloorCnt", detail: item.shuttleFloorCnt)
                                    Row(title: "shuttleSection", detail: item.shuttleSection)
                                    Row(title: "sigunguNm", detail: item.sigunguNm)
                                    Row(title: "undgrndFloorCnt", detail: item.undgrndFloorCnt)
                                     // */
                                }
                            }
                        }
                    }
                    .listStyle(.insetGrouped)
                    .padding([.horizontal])
                    NavigationLink(destination: MainView()) {
                        Text("Main")
                            .font(.largeTitle)
                            .foregroundColor(.white)
                    }
                    .frame(maxWidth: .infinity, maxHeight: 90.0)
                    .background(Color.selected)
                    .cornerRadius(16.0)
                    .padding([.horizontal], 16.0)
                }
            }
            .statusBarHidden(true)
            .navigationBarHidden(true)
            .onAppear(perform: {
                // 화면 나타날 때 자료 가져와서 표시하자.
                if let responseElevator = userStore.responseElevator {
                    text = responseElevator.response.body.items[0].resultNm
                } else {
                    text = "error"
                }
            })
        }
    }
}

struct Row: View {
    var title = "제목"
    var detail = "내용입니다. 주저리 주저리"
    let width = UIScreen.main.bounds.width * 0.2
    var body: some View {
        HStack {
            Text(title)
                .font(.body)
                .fixedSize()
                .foregroundColor(.text)
                .frame(maxWidth: width, alignment: .leading)
            Divider()
            Text(detail)
                .font(.body)
                .foregroundColor(.text)
        }
    }
}

struct ElevatorResultView_Previews: PreviewProvider {
    static var previews: some View {
        ElevatorResultView().environmentObject(UserStore())
    }
}
