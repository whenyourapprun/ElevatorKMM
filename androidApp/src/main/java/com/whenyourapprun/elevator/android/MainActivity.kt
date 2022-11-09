package com.whenyourapprun.elevator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whenyourapprun.elevator.Elevator
import com.whenyourapprun.elevator.android.ui.theme.ElevatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElevatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
private fun MainApp() {
    Scaffold(
        bottomBar = { BottomNavigation() }
    ) { padding ->
        MainScreen(Modifier.padding(padding))
    }
}

@Composable
private fun MainScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.LightGray)
    ) {
        MainView("Top View")
        val scope = rememberCoroutineScope()
        var text by remember { mutableStateOf("Loading") }
        LaunchedEffect(key1 = true) {
            text = try {
                Elevator().getElevatorInfo("8088381").response.header.resultMsg
            } catch (e: Exception) {
                e.localizedMessage ?: "error"
            }
        }
        MainView(text)
        MainView("Bottom View")
    }
}

@Composable
private fun MainView(string: String) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
    ) {
        Column() {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                textAlign = TextAlign.Center,
                fontSize = 21.sp,
                color = Color.DarkGray,
                text = string
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun BottomNavigation(modifier: Modifier = Modifier) {
    BottomNavigation(modifier) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.app_name))
            },
            selected = true,
            onClick = {}
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.app_name))
            },
            selected = false,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun PreviewMain() {
    ElevatorTheme {
        MainApp()
    }
}