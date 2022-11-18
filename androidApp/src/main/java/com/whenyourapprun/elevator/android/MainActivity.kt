package com.whenyourapprun.elevator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whenyourapprun.elevator.android.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElevatorTheme {
                MainCompose("Android")
            }
        }
    }
}

@Composable
fun MainCompose(name: String) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.LightGray)
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .weight(9f)) {
                LazyColumn {
                    item {
                        ContentCard(stringResource(id = R.string.Elevator), stringResource(id = R.string.ElevatorGuide), onClickSource = {
                            print("1 click")
                        })
                        ContentCard("Title 2", "detail 2", onClickSource = {
                            print("2 click")
                        })
                        ContentCard("Title 3", "detail 3", onClickSource = {
                            print("3 click")
                        })
                        ContentCard("Title 4", "detail 4", onClickSource = {
                            print("4 click")
                        })
                    }
                }
            }
            Box(modifier = Modifier.fillMaxSize().weight(1f)) {
                BottomView()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentCard(title: String, detail: String, onClickSource: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(16.dp),
        onClick = { onClickSource }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
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
fun BottomView() {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        MainBottomButton("1")
        MainBottomButton("2")
        MainBottomButton("3")
        MainBottomButton("4")
    }
}

@Composable
fun MainBottomButton(title: String){
    Surface(
        color = Color.Transparent,
        modifier = Modifier.height(70.dp)
    ){
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black, disabledBackgroundColor = Color.LightGray, disabledContentColor = Color.Black)
        ) {
            Text(text = title)
        }
    }
}

@Preview
@Composable
fun PreviewMain() {
    ElevatorTheme {
        MainCompose("Android")
    }
}