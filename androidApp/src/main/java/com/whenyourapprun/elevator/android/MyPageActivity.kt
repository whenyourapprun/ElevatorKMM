package com.whenyourapprun.elevator.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.lifecycle.lifecycleScope
import com.whenyourapprun.elevator.Item
import com.whenyourapprun.elevator.android.ui.theme.ElevatorTheme
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

data class PlayItem(
    val number: String,
    val build: String,
    val date: String
)

class MyPageActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MyPageActivity"
    }
    private val util = Utility()
    private var nick = ""
    private var playItemList = ArrayList<PlayItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 별명 가져오기
        nick = util.getNick(applicationContext)
        if (nick == "") {
            nick = getString(R.string.NickSample)
        }
        // 실행이력 내부 디비에서 가져오기.
        val dbHelper = DBHelper(applicationContext)
        val sqlDB = dbHelper.readableDatabase
        val sql = "SELECT number, build, date FROM playTable ORDER BY date DESC LIMIT 20"
        val cursor = sqlDB.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val number = cursor.getString(0)
            val build = cursor.getString(1)
            val date = cursor.getString(2)
            val addItem = PlayItem(number, build, date)
            playItemList.add(addItem)
        }
        Log.d(TAG, "playItemList count ${playItemList.count()}")
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
                        // 별명 변경 버튼과 등록된 별명
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .background(colorResource(id = R.color.not_selected)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                modifier = Modifier
                                    .wrapContentSize(),
                                onClick = {
                                    val intent = Intent(context, NickActivity::class.java)
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
                                Text(stringResource(id = R.string.NickChange))
                            }
                            Text(text = nick, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                        }
                        // 가운데
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(7.5f)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // 리스트 아이템으로 내용 채움
                                LazyColumn {
                                    itemsIndexed(playItemList) { index, item ->
                                        Box(contentAlignment = Alignment.Center) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier
                                                    .padding(16.dp)
                                                    .background(color = colorResource(id = R.color.white))
                                            ) {
                                                Row {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .weight(1f)
                                                    ) {
                                                        Text(
                                                            text = item.number,
                                                            textAlign = TextAlign.Center,
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .padding(vertical = 16.dp)
                                                        )
                                                    }
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .weight(1f)
                                                    ) {
                                                        Text(
                                                            text = item.build,
                                                            textAlign = TextAlign.Center,
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .padding(vertical = 16.dp)
                                                        )
                                                    }
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .weight(1f)
                                                    ) {
                                                        Text(
                                                            text = item.date,
                                                            textAlign = TextAlign.Center,
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .padding(vertical = 16.dp)
                                                        )
                                                    }
                                                }
                                                Divider()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // 하단
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(70.dp)
                                    .padding(horizontal = 16.dp),
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