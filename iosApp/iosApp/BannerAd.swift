//
//  BannerAd.swift
//  iosApp
//
//  Created by whenyourapprun on 2022/11/13.
//  Copyright © 2022 orgName. All rights reserved.
//
import SwiftUI
import GoogleMobileAds

struct BannerAd: UIViewRepresentable {
    var unitID: String

    func makeCoordinator() -> Coordinator {
        // For Implementing Delegates..
        return Coordinator()
    }

    func makeUIView(context: Context) -> GADBannerView{
        let adView = GADBannerView(adSize: GADAdSizeBanner)

        adView.adUnitID = unitID
        adView.rootViewController = UIApplication.shared.getRootViewController()

        adView.load(GADRequest())

        return adView
    }

    func updateUIView(_ uiView: GADBannerView, context: Context) {

    }

    class Coordinator: NSObject, GADBannerViewDelegate {
        func bannerViewDidReceiveAd(_ bannerView: GADBannerView) {
            print("bannerViewDidReceiveAd")
        }

        func bannerView(_ bannerView: GADBannerView, didFailToReceiveAdWithError error: Error) {
            print("bannerView:didFailToReceiveAdWithError: \(error.localizedDescription)")
        }

        func bannerViewDidRecordImpression(_ bannerView: GADBannerView) {
            print("bannerViewDidRecordImpression")
        }

        func bannerViewWillPresentScreen(_ bannerView: GADBannerView) {
            print("bannerViewWillPresentScreen")
        }

        func bannerViewWillDismissScreen(_ bannerView: GADBannerView) {
            print("bannerViewWillDIsmissScreen")
        }

        func bannerViewDidDismissScreen(_ bannerView: GADBannerView) {
            print("bannerViewDidDismissScreen")
        }
    }
}

// Extending Application to get RootView..
extension UIApplication {
    func getRootViewController() -> UIViewController {

        guard let screen = self.connectedScenes.first as? UIWindowScene else {
            return .init()
        }

        guard let root = screen.windows.first?.rootViewController else {
            return .init()
        }

        return root
    }
}
