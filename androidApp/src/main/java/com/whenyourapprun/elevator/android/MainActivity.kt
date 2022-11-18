package com.whenyourapprun.elevator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
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
                Surface(color = Color.LightGray) {
                    MainCompose("Android")
                }
            }
        }
    }
}

@Composable
fun MainCompose(name: String) {
    Column {
        LazyColumn {
            item {
                ContentCard(stringResource(id = R.string.Elevator), stringResource(id = R.string.ElevatorGuide))
                ContentCard("Title 2", "detail 2")
                //ContentCard("Title 3", "detail 3")
                //ContentCard("Title 4", "detail 4")
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        BottomView()
    }
}

@Composable
fun ContentCard(title: String, detail: String) {
    Card(
        Modifier
            .padding(16.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(16.dp)
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
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            .weight(1f, fill = true)
            .padding(8.dp)) {
            MainBottomButton("1")
        }
        Box(modifier = Modifier
            .weight(1f, fill = true)
            .padding(8.dp)) {
            MainBottomButton("2")
        }
        Box(modifier = Modifier
            .weight(1f, fill = true)
            .padding(8.dp)) {
            MainBottomButton("3")
        }
        Box(modifier = Modifier
            .weight(1f, fill = true)
            .padding(8.dp)) {
            MainBottomButton("4")
        }
    }
}

@Composable
fun MainBottomButton(title: String){
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .layout { measurable, constraints ->
                val placeable = measurable.measure((constraints))
                layout(placeable.width, placeable.width) {
                    placeable.place(x = 0, y = 0, zIndex = 0f)
                }
            }
            .fillMaxSize()
    ){
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray, contentColor = Color.White, disabledBackgroundColor = Color.LightGray, disabledContentColor = Color.Black)
        ) {
            Text(text = title, color = Color.White)
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