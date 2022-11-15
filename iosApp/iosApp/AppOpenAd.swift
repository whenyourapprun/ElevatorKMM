//
//  AppOpenAd.swift
//  Elevator
//
//  Created by whenyourapprun on 2022/11/15.
//  Copyright © 2022 orgName. All rights reserved.
//
import GoogleMobileAds

class AppOpen: NSObject, GADFullScreenContentDelegate, ObservableObject {

    @Published var appOpenAdLoaded: Bool = false
    var appOpenAd: GADAppOpenAd?

    override init() {
        super.init()
        LoadAppOpenAd()
    }

    func LoadAppOpenAd() {
        let request = GADRequest()
        GADAppOpenAd.load(
            withAdUnitID: openId,
            request: request,
            orientation: UIInterfaceOrientation.portrait
        ) { appOpenAdIn, _ in
            self.appOpenAd = appOpenAdIn
            self.appOpenAd?.fullScreenContentDelegate = self
            self.appOpenAdLoaded = true
            print("LoadAppOpenAd")
        }
    }

    func ShowAppOpenAd() {
        guard let root = self.appOpenAd else { return }
        root.present(fromRootViewController: (UIApplication.shared.windows.first?.rootViewController)!)
    }

    func ad(_ ad: GADFullScreenPresentingAd, didFailToPresentFullScreenContentWithError error: Error) {
        self.LoadAppOpenAd()
        print("didFailToPresentFullScreenContentWithError \(error.localizedDescription)")
    }

    func adDidDismissFullScreenContent(_ ad: GADFullScreenPresentingAd) {
        self.LoadAppOpenAd()
        print("AppOpenAd adDidDismissFullScreenContent")
    }
}
