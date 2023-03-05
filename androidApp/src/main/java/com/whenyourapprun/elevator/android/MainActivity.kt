package com.whenyourapprun.elevator.android

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.*
import com.whenyourapprun.elevator.android.ui.theme.*

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }
    // full_ad
    //private var fullAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 전면 광고
        //MobileAds.initialize(this) {
        //    loadAd()
        //}
        setContent {
            ElevatorTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val context = LocalContext.current
                val adWidth = LocalConfiguration.current.screenWidthDp
                // 발판 사용 - 머터리얼
                Scaffold(scaffoldState = scaffoldState) {
                    // 전체 적용 및 배경 색상 설정
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .background(colorResource(id = R.color.back))
                    ) {
                        // admob banner
                        AndroidView(
                            modifier = Modifier.fillMaxWidth(),
                            factory = { context ->
                                AdView(context).apply {
                                    setAdSize(
                                        AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
                                    )
                                    adUnitId = context.getString(R.string.bannerId)
                                    loadAd(AdRequest.Builder().build())
                                }
                            }
                        )
                        // 가운데 90% 크기
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(9f)
                        ) {
                            // 리스트 아이템으로 내용 채움
                            LazyColumn {
                                item {
                                    // 엘리베이터 카드
                                    Card(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxSize()
                                            .clickable {
                                                // 광고가 로딩 되었으면 광고 부터 보이자
                                                //if (fullAd != null) {
                                                //    fullAd?.show(context.findActivity())
                                                //} else {
                                                    val intent = Intent(
                                                        context,
                                                        ElevatorActivity::class.java
                                                    )
                                                    context.startActivity(intent)
                                                //}
                                            },
                                        shape = RoundedCornerShape(16.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier.padding(horizontal = 16.dp)
                                            ) {
                                                Spacer(modifier = Modifier.height(32.dp))
                                                Text(text = stringResource(id = R.string.Elevator), fontSize = 34.sp, fontWeight = FontWeight.Bold)
                                                Spacer(modifier = Modifier.height(16.dp))
                                                Text(text = stringResource(id = R.string.ElevatorGuide), fontSize = 21.sp)
                                                Spacer(modifier = Modifier.height(32.dp))
                                            }
                                        }
                                    }
                                    // 마이페이지 카드
                                    ContentCard(
                                        stringResource(id = R.string.MyPage),
                                        stringResource(id = R.string.MyPageGuide)
                                    )
                                    // 별명 변경 카드
                                    ContentCard(
                                        stringResource(id = R.string.NickChange),
                                        stringResource(id = R.string.NickChangeGuide)
                                    )
                                }
                            }
                        }
                        // 하단 높이 10%
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .background(color = colorResource(id = R.color.not_selected))
                        ) {
                            // 하단 뷰
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                ) {
                                    MainBottomButton("⇧")
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                ) {
                                    MainBottomButton("⚙")
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                ) {
                                    MainBottomButton("♺")
                                }
                            }
                        }
                    }
                } // end_Scaffold
            } // end_ElevatorTheme
        } // end_setContent
    } // end_onCreate

    /*
    private fun loadAd() {
        InterstitialAd.load(
            this,
            getString(R.string.fullId),
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    fullAd = ad
                    ad.fullScreenContentCallback = object: FullScreenContentCallback() {
                        override fun onAdClicked() {
                            // Called when a click is recorded for an ad.
                            Log.d(TAG, "Ad was clicked.")
                        }
                        override fun onAdDismissedFullScreenContent() {
                            // Called when ad is dismissed.
                            // Set the ad reference to null so you don't show the ad a second time.
                            Log.d(TAG, "Ad dismissed fullscreen content.")
                            fullAd = null
                            // 광고를 다 보고 창을 닫을 때 ElevatorActivity
                            val intent = Intent(applicationContext, ElevatorActivity::class.java)
                            startActivity(intent)
                        }
                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            // Called when ad fails to show.
                            Log.e(TAG, "Ad failed to show fullscreen content.")
                            fullAd = null
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
                    fullAd = null
                }
            }
        )
    }
     */
} // end_MainActivity

@Composable
fun ContentCard(title: String, detail: String) {
    val myPage = stringResource(id = R.string.MyPage)
    val nickChange = stringResource(id = R.string.NickChange)
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .clickable {
                Log.d(TAG, "content card touch $title")
                // 선택한 곳에 따라 이동
                if (title == myPage) {
                    val intent = Intent(context, MyPageActivity::class.java)
                    context.startActivity(intent)
                } else if (title == nickChange) {
                    val intent = Intent(context, NickActivity::class.java)
                    context.startActivity(intent)
                }
            },
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(text = title, fontSize = 34.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = detail, fontSize = 21.sp)
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun MainBottomButton(title: String){
    val context = LocalContext.current
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ){
        Button(
            onClick = {
                // 선택한 곳에 따라 이동
                if (title == "⇧") {
                    val intent = Intent(context, ElevatorActivity::class.java)
                    context.startActivity(intent)
                } else if (title == "⚙") {
                    val intent = Intent(context, MyPageActivity::class.java)
                    context.startActivity(intent)
                } else if (title == "♺") {
                    val intent = Intent(context, NickActivity::class.java)
                    context.startActivity(intent)
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.selected), 
                contentColor = colorResource(id = R.color.white), 
                disabledBackgroundColor = colorResource(id = R.color.not_selected), 
                disabledContentColor = colorResource(id = R.color.text)
            )
        ) {
            Text(text = title)
        }
    }
}
