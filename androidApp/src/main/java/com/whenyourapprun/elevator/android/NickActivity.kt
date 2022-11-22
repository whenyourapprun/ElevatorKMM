package com.whenyourapprun.elevator.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.whenyourapprun.elevator.android.ui.theme.ElevatorTheme
import kotlinx.coroutines.launch

class NickActivity : ComponentActivity(), OnUserEarnedRewardListener {
    companion object {
        private const val TAG = "NickActivity"
    }
    private val util = Utility()
    // reward full ad
    private var rewardedInterstitialAd: RewardedInterstitialAd? = null

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // AdMob
        MobileAds.initialize(this) { initializationStatus ->
            loadAd()
        }
        setContent {
            ElevatorTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val (text, setValue) = remember { mutableStateOf("") }
                val keyboardController = LocalSoftwareKeyboardController.current
                val context = LocalContext.current
                // 발판 사용 - 머터리얼
                Scaffold(scaffoldState = scaffoldState) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .background(colorResource(id = R.color.back))
                            .clickable {
                                // 아무곳이나 터치하면 키보드 감추기
                                keyboardController?.hide()
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp)
                                .background(Color.White)
                        ) {
                            Column() {
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    value = text,
                                    onValueChange = setValue,
                                    label = { Text(stringResource(id = R.string.NickInput)) },
                                    placeholder = { Text(text = util.getNick(context = context)) },
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                    keyboardActions = KeyboardActions(
                                        onDone = { keyboardController?.hide() }
                                    )
                                )
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .padding(16.dp),
                                    onClick = {
                                        // 키보드 감추기
                                        keyboardController?.hide()
                                        /*/ 스낵바로 입력 창 띄워보자
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("input nick $text")
                                        }
                                        // 로그 찍어보자
                                        Log.d(TAG, "input nick $text")
                                        // */
                                        if (text != "") {
                                            util.setNick(context = context, text)
                                        }
                                        // 광고 로딩 되었으면 띄운다.
                                        if (rewardedInterstitialAd != null) {
                                            rewardedInterstitialAd?.show(this@NickActivity, this@NickActivity)
                                        } else {
                                            // 메인 창으로 이동
                                            val intent = Intent(context, MainActivity::class.java)
                                            context.startActivity(intent)
                                            // 뒤로 가기 했을 때 나오지 않도록 종료
                                            finish()
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = colorResource(id = R.color.selected),
                                        contentColor = colorResource(id = R.color.white),
                                        disabledBackgroundColor = colorResource(id = R.color.not_selected),
                                        disabledContentColor = colorResource(id = R.color.text)
                                    )
                                ) {
                                    Text(stringResource(id = R.string.OK))
                                }
                            }
                        }
                    }
                } // end_Scaffold
            } // end_ElevatorTheme
        } // end_setContent
    } // end_onCreate

    private fun loadAd() {
        RewardedInterstitialAd.load(this, getString(R.string.rewardFullId),
            AdRequest.Builder().build(), object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    rewardedInterstitialAd = ad
                    // callback
                    rewardedInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                        override fun onAdClicked() {
                            // Called when a click is recorded for an ad.
                            Log.d(TAG, "Ad was clicked.")
                        }
                        override fun onAdDismissedFullScreenContent() {
                            // Called when ad is dismissed.
                            // Set the ad reference to null so you don't show the ad a second time.
                            Log.d(TAG, "Ad dismissed fullscreen content.")
                            rewardedInterstitialAd = null
                            // 광고 화면이 끝나면 메인 창으로 이동
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            // 뒤로 가기 했을 때 나오지 않도록 종료
                            finish()
                        }
                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            // Called when ad fails to show.
                            Log.e(TAG, "Ad failed to show fullscreen content.")
                            rewardedInterstitialAd = null
                        }
                        override fun onAdImpression() {
                            // Called when an impression is recorded for an ad.
                            Log.d(TAG, "Ad recorded an impression.")
                        }
                        override fun onAdShowedFullScreenContent() {
                            // Called when ad is shown.
                            Log.d(TAG, "Ad showed fullscreen content.")
                        }
                    }
                }
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    rewardedInterstitialAd = null
                }
            })
    }

    override fun onUserEarnedReward(rewardItem: RewardItem) {
        Log.d(TAG, "User earned reward.")
    }
} // end_NickActivity
