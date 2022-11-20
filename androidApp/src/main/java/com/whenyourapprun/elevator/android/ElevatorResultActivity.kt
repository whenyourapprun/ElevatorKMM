package com.whenyourapprun.elevator.android

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whenyourapprun.elevator.Item
import com.whenyourapprun.elevator.android.ui.theme.ElevatorTheme
import java.io.Serializable

class ElevatorResultActivity : ComponentActivity() {
    companion object {
        private const val TAG = "ElevatorResultActivity"
    }
    private val util = Utility()
    private lateinit var elevatorItemList: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        elevatorItemList = util.getElevatorItems(applicationContext)
        setContent {
            ElevatorTheme {
                Text(text = elevatorItemList[0].resultNm)
            }
        }
    }

}
