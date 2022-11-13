//
//  Colors.swift
//  iosApp
//
//  Created by whenyourapprun on 2022/11/09.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared
import Combine

let bannerId = "ca-app-pub-4377563560880478/2336045802"
let fullId = "ca-app-pub-4377563560880478/6393683610"
let rewardFullId = "ca-app-pub-4377563560880478/6049448817"
let rewardId = "ca-app-pub-4377563560880478/6410758501"
let openId = "ca-app-pub-4377563560880478/3767520279"

extension Color {
    // 공통으로 사용할 색상
    static let back = Color(hex: "#F3F4F5")
    static let text = Color(hex: "#2D2F36")
    static let selected = Color(hex: "#353D4B")
    static let not_selected = Color(hex: "#D2D6DA")
    
    init(hex: String) {
        let scanner = Scanner(string: hex)
        _ = scanner.scanString("#")
        
        var rgb: UInt64 = 0
        scanner.scanHexInt64(&rgb)
        
        let r = Double((rgb >> 16) & 0xFF) / 255.0
        let g = Double((rgb >>  8) & 0xFF) / 255.0
        let b = Double((rgb >>  0) & 0xFF) / 255.0
        self.init(red: r, green: g, blue: b)
    }
}

extension LocalizedStringKey {
    var stringKey: String? {
        Mirror(reflecting: self).children.first(where: { $0.label == "key" })?.value as? String
    }
    
    func stringValue(locale: Locale = .current) -> String {
        return .localizedString(for: self.stringKey!, locale: locale)
    }
}

extension String {
    static func localizedString(for key: String, locale: Locale = .current) -> String {
        let language = locale.languageCode
        let path = Bundle.main.path(forResource: language, ofType: "lproj")!
        let bundle = Bundle(path: path)!
        let localizedString = NSLocalizedString(key, bundle: bundle, comment: "")
        
        return localizedString
    }
}

// 앱 전체에서 사용할 자료 구조 여기에 추가
class UserStore: ObservableObject {
    // 엘리베이터 검사 정보 가져오는 구조체
    @Published var responseElevator: ResponseElevator?
}
