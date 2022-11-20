package com.whenyourapprun.elevator.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whenyourapprun.elevator.android.ui.theme.ElevatorTheme

class MyPageActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MyPageActivity"
    }
    private val util = Utility()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElevatorTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val context = LocalContext.current
                // 발판 사용 - 머터리얼
                Scaffold(scaffoldState = scaffoldState) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .background(colorResource(id = R.color.back)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // 상단
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1.5f)
                                .padding(16.dp)
                                .background(color = colorResource(id = R.color.white))
                        ) {
                            Text(
                                text = stringResource(R.string.MyPage),
                                color = colorResource(id = R.color.text),
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(colorResource(id = R.color.not_selected))
                                    .padding(vertical = 16.dp)
                            )
                        }
                        // 가운데
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(7f)
                        ) {
                            Text(text = "MyPageActivity")
                        }
                        // 하단
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1.5f)
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(16.dp),
                                onClick = {
                                    // 일단 화면 전환을 하자.
                                    val intent = Intent(context, MainActivity::class.java)
                                    context.startActivity(intent)
                                    finish()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.selected),
                                    contentColor = colorResource(id = R.color.white),
                                    disabledBackgroundColor = colorResource(id = R.color.not_selected),
                                    disabledContentColor = colorResource(id = R.color.text)
                                )
                            ) {
                                Text(stringResource(id = R.string.Main))
                            }
                        }
                    } // end_Column
                } // end_Scaffold
            } // end_ElevatorTheme
        } // end_setContent
    } // end_onCreate
}