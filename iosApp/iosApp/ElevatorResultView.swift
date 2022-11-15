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
    // 전역 변수
    @EnvironmentObject var userStore: UserStore
    // 전면 광고
    @State var showIntersitialAd: Bool = false
    // 지역 변수
    @State private var text = ""
    // 다국어 처리
    private let ResultLoading: LocalizedStringKey = "Loading"
    private let ResultMain: LocalizedStringKey = "Main"
    private let ResultError: LocalizedStringKey = "Error"
    private let address1: LocalizedStringKey = "address1"
    private let address2: LocalizedStringKey = "address2"
    private let applcBeDt: LocalizedStringKey = "applcBeDt"
    private let applcEnDt: LocalizedStringKey = "applcEnDt"
    private let areaNm: LocalizedStringKey = "areaNm"
    private let buldMgtNo1: LocalizedStringKey = "buldMgtNo1"
    private let buldMgtNo2: LocalizedStringKey = "buldMgtNo2"
    private let buldNm: LocalizedStringKey = "buldNm"
    private let elevatorNo: LocalizedStringKey = "elevatorNo"
    private let elvtrAsignNo: LocalizedStringKey = "elvtrAsignNo"
    private let elvtrDetailForm: LocalizedStringKey = "elvtrDetailForm"
    private let elvtrDivNm: LocalizedStringKey = "elvtrDivNm"
    private let elvtrForm: LocalizedStringKey = "elvtrForm"
    private let elvtrKindNm: LocalizedStringKey = "elvtrKindNm"
    private let elvtrSttsNm: LocalizedStringKey = "elvtrSttsNm"
    private let frstInstallationDe: LocalizedStringKey = "frstInstallationDe"
    private let groundFloorCnt: LocalizedStringKey = "groundFloorCnt"
    private let installationDe: LocalizedStringKey = "installationDe"
    private let installationPlace: LocalizedStringKey = "installationPlace"
    private let liveLoad: LocalizedStringKey = "liveLoad"
    private let ratedCap: LocalizedStringKey = "ratedCap"
    private let resultNm: LocalizedStringKey = "resultNm"
    private let shuttleFloorCnt: LocalizedStringKey = "shuttleFloorCnt"
    private let shuttleSection: LocalizedStringKey = "shuttleSection"
    private let sigunguNm: LocalizedStringKey = "sigunguNm"
    private let undgrndFloorCnt: LocalizedStringKey = "undgrndFloorCnt"
    // 뷰 전환
    @State private var showNextView = false
    
    var body: some View {
        ZStack {
            Color.back.edgesIgnoringSafeArea(.all)
            VStack {
                Text(text)
                    .font(.title)
                    .minimumScaleFactor(0.5)
                    .lineLimit(1)
                    .foregroundColor(.text)
                    .padding([.vertical])
                    .frame(maxWidth: .infinity, alignment: .center)
                    .background(Color.white)
                    .cornerRadius(16.0)
                    .padding([.horizontal])
                List {
                    if let itemList = userStore.responseElevator?.response.body.items {
                        ForEach(itemList, id: \.self) { item in
                            Section {
                                Row(title: elevatorNo, detail: item.elevatorNo)
                                Row(title: elvtrAsignNo, detail: item.elvtrAsignNo)
                                Row(title: areaNm, detail: item.areaNm)
                                Row(title: sigunguNm, detail: item.sigunguNm)
                                Row(title: address1, detail: item.address1)
                                Row(title: address2, detail: item.address2)
                                Row(title: buldNm, detail: item.buldNm)
                                Row(title: buldMgtNo1, detail: item.buldMgtNo1)
                                Row(title: buldMgtNo2, detail: item.buldMgtNo2)
                                Row(title: resultNm, detail: item.resultNm)
                                Row(title: applcBeDt, detail: item.applcBeDt)
                                Row(title: applcEnDt, detail: item.applcEnDt)
                                Row(title: elvtrDivNm, detail: item.elvtrDivNm)
                                Row(title: elvtrForm, detail: item.elvtrForm)
                                Row(title: elvtrKindNm, detail: item.elvtrKindNm)
                                Row(title: elvtrDetailForm, detail: item.elvtrDetailForm)
                                Row(title: elvtrSttsNm, detail: item.elvtrSttsNm)
                                Row(title: frstInstallationDe, detail: item.frstInstallationDe)
                                Row(title: installationDe, detail: item.installationDe)
                                Row(title: installationPlace, detail: item.installationPlace)
                                Row(title: liveLoad, detail: item.liveLoad)
                                Row(title: ratedCap, detail: item.ratedCap)
                                Row(title: shuttleFloorCnt, detail: item.shuttleFloorCnt)
                                Row(title: shuttleSection, detail: item.shuttleSection)
                                Row(title: groundFloorCnt, detail: item.groundFloorCnt)
                                Row(title: undgrndFloorCnt, detail: item.undgrndFloorCnt)
                            }
                        }
                    }
                }
                .listStyle(.insetGrouped)
                .padding([.horizontal])
                Text(ResultMain)
                    .font(.largeTitle)
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity, maxHeight: 90.0)
                    .background(Color.selected)
                    .cornerRadius(16.0)
                    .padding([.horizontal])
                    .onTapGesture {
                        // 내부 디비에 검색 결과를 저장하자.
                        if let responseElevator = userStore.responseElevator {
                            let number = responseElevator.response.body.items[0].elevatorNo
                            let build = responseElevator.response.body.items[0].buldNm
                            let df = DateFormatter()
                            df.dateFormat = "yyyy.MM.dd HH:mm:ss"
                            let date = df.string(from: Date())
                            print("number \(number), build \(build), date \(date)")
                            DBHelper().insertData(number: number, build: build, date: date)
                        }
                        // 전면광고 띄우고
                        showIntersitialAd = true
                    }
            }
        }
        .statusBarHidden(true)
        .onAppear(perform: {
            // 화면 나타날 때 자료 가져와서 표시하자.
            if let responseElevator = userStore.responseElevator {
                text = "\(resultNm.stringValue()) :  \(responseElevator.response.body.items[0].resultNm)"
            } else {
                text = "\(resultNm.stringValue()) : \(ResultError.stringValue())"
            }
        })
        .presentInterstitialAd(isPresented: $showIntersitialAd, adUnitId: fullId)
        .onReceive(NotificationCenter.default.publisher(for: .interstitialAdDidDismissFullScreenContent)) { _ in
            // 전면광고 끝났을 때 메인 창으로
            showNextView = true
        }
        .fullScreenCover(isPresented: $showNextView) {
            MainView()
        }
    }
}

struct Row: View {
    var title: LocalizedStringKey
    var detail: String = ""
    let width = UIScreen.main.bounds.width * 0.3
    var body: some View {
        VStack {
            Text(title)
                .font(.body)
                .minimumScaleFactor(0.5)
                .fixedSize()
                .foregroundColor(.text)
                .frame(maxWidth: .infinity, alignment: .center)
            Text(detail)
                .font(.body)
                .minimumScaleFactor(0.5)
                .lineLimit(1)
                .foregroundColor(.text)
                .padding([.vertical])
                .frame(maxWidth: .infinity, alignment: .center)
                .background(Color.not_selected)
        }
    }
}

// View 10개 이상이면 오류나서 이걸 추가하면 오류 나지 않음.
extension ViewBuilder {
    public static func buildBlock<C0, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15, C16, C17, C18, C19, C20, C21, C22, C23, C24, C25>
    (_ c0: C0, _ c1: C1, _ c2: C2, _ c3: C3, _ c4: C4, _ c5: C5, _ c6: C6, _ c7: C7, _ c8: C8, _ c9: C9, _ c10: C10, _ c11: C11, _ c12: C12, _ c13: C13, _ c14: C14, _ c15: C15, _ c16: C16, _ c17: C17, _ c18: C18, _ c19: C19, _ c20: C20, _ c21: C21, _ c22: C22, _ c23: C23, _ c24: C24, _ c25: C25)
        -> TupleView<(
                Group<TupleView<(C0, C1, C2, C3, C4, C5, C6, C7, C8, C9)>>,
                Group<TupleView<(C10, C11, C12, C13, C14, C15, C16, C17, C18, C19)>>,
                Group<TupleView<(C20, C21, C22, C23, C24, C25)>>
            )>
    where C0: View, C1: View, C2: View, C3: View, C4: View, C5: View, C6: View, C7: View, C8: View, C9: View, C10: View, C11: View, C12: View, C13: View, C14: View, C15: View, C16: View, C17: View, C18: View, C19: View, C20: View, C21: View, C22: View, C23: View, C24: View, C25: View {
        TupleView(
            (
                Group { TupleView((c0, c1, c2, c3, c4, c5, c6, c7, c8, c9)) },
                Group { TupleView((c10, c11, c12, c13, c14, c15, c16, c17, c18, c19)) },
                Group { TupleView((c20, c21, c22, c23, c24, c25)) }
            )
        )
    }
}

struct ElevatorResultView_Previews: PreviewProvider {
    static var previews: some View {
        ElevatorResultView()
            .environmentObject(UserStore())
            .environment(\.locale, .init(identifier: "ko"))
    }
}
