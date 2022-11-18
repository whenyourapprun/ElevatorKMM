package com.whenyourapprun.elevator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.whenyourapprun.elevator.android.ui.theme.ElevatorTheme

class ElevatorActivity : ComponentActivity() {
    companion object {
        private const val TAG = "ElevatorActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        Text(text = "ElevatorActivity")
                    }
                }
            }
        }
    }
}