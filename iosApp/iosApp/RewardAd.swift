//
//  RewardAd.swift
//  iosApp
//
//  Created by whenyourapprun on 2022/11/13.
//  Copyright © 2022 orgName. All rights reserved.
//
import GoogleMobileAds
import UIKit

class RewardAd: NSObject, GADFullScreenContentDelegate, ObservableObject {
    @Published var rewardLoaded: Bool = false
    var rewardedAd: GADRewardedAd?

    override init() {
        super.init()
    }

    func LoadReward() {
        GADRewardedAd.load(withAdUnitID: rewardId, request: GADRequest()) { (ad, error) in
            if let e = error {
                print("RewardAd load error : \(e.localizedDescription)")
                self.rewardLoaded = false
                return
            }
            print("load success")
            self.rewardLoaded = true
            self.rewardedAd = ad
            self.rewardedAd?.fullScreenContentDelegate = self
        }
    }

    func ShowReward() {
        let root = UIApplication.shared.windows.first?.rootViewController
        if let ad = rewardedAd {
            ad.present(fromRootViewController: root!, userDidEarnRewardHandler: {
                print("earn reward")
                self.rewardLoaded = false
            })
        } else {
            print("failed")
            self.rewardLoaded = false
            self.LoadReward()
        }
    }
}
