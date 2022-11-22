package com.whenyourapprun.elevator.android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.whenyourapprun.elevator.android.ui.theme.ElevatorTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timer

class IntroActivity : ComponentActivity() {
    companion object {
        private const val TAG = "IntroActivity"
    }
    private val util = Utility()
    // 타이머에서 사용할 변수
    private var seconds by mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // uuid 한번만 실행
        val uuid = util.getUUID(applicationContext)
        if (uuid == "") {
            util.setUUID(applicationContext, UUID.randomUUID().toString())
        }
        lifecycleScope.launch {

        }
        // 타이머 생성 period 200 줘야 광고 로딩 후 뜬다
        timer(period = 10) {
            seconds++
            if (seconds > 100) {
                // 타이머 중지
                this.cancel()
                // countdown finish 오프닝 광고 띄우기 위해
                val application = application as? MyApplication
                if (application == null) {
                    // 메인화면으로 이동
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                lifecycleScope.launch(Dispatchers.Main) {
                    // Show the app open ad.
                    application?.showAdIfAvailable(this@IntroActivity, object : MyApplication.OnShowAdCompleteListener {
                        override fun onShowAdComplete() {
                            // 메인화면으로 이동
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })
                }

            }
        }
        // AdMob 초기화
        MobileAds.initialize(this) {}
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
        )
        setContent {
            ElevatorTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                // 발판 사용 - 머터리얼
                Scaffold(scaffoldState = scaffoldState) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .background(colorResource(id = R.color.back)),
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
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp, 8.dp)
                                        .background(Color.White),
                                    textAlign = TextAlign.Center,
                                    fontSize = 21.sp,
                                    color = colorResource(id = R.color.text),
                                    text = stringResource(R.string.Elevator)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                LinearProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(16.dp)
                                        .padding(16.dp, 0.dp),
                                    backgroundColor = colorResource(id = R.color.not_selected),
                                    color = colorResource(id = R.color.selected),
                                    progress = seconds.toFloat() / 100.0f
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
