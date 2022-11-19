package com.whenyourapprun.elevator.android

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.whenyourapprun.elevator.Elevator
import com.whenyourapprun.elevator.android.ui.theme.ElevatorTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class ElevatorActivity : ComponentActivity() {
    companion object {
        private const val TAG = "ElevatorActivity"
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ElevatorTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                //var text by remember { mutableStateOf(TextFieldValue("")) }
                var textFieldValue = remember {
                    mutableStateOf(
                        TextFieldValue(
                            text = "",
                            selection = TextRange(0)
                        )
                    )
                }
                val focusManager = LocalFocusManager.current
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
                                    value = textFieldValue.value,
                                    onValueChange = {
                                        var newText = it.text
                                        if (it.text.length == 4) {
                                            newText = it.text.plus("-")
                                        }
                                        if (it.text.length > 8) {
                                            newText = ""
                                        }
                                        textFieldValue.value = TextFieldValue(
                                            text = newText,
                                            selection = TextRange(newText.length)
                                        )
                                    },
                                    label = { Text(stringResource(id = R.string.ElevatorInputGuide)) },
                                    placeholder = { Text(text = "8088-381") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            // cancel focus and hide keyboard
                                            focusManager.clearFocus()
                                            keyboardController?.hide()
                                        }
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
                                            scaffoldState.snackbarHostState.showSnackbar("elevator no ${textFieldValue.value}")
                                        }
                                        // 로그 찍어보자
                                        Log.d(TAG, "elevator no ${textFieldValue.value}")
                                        // */
                                        scope.launch {
                                            // 엘리베이터 번호 중에 숫자만 추출하자.
                                            val elevatorNo = textFieldValue.value.text.replace("-", "")
                                            Log.d(TAG, "elevatorNo $elevatorNo")
                                            val itemList = Elevator().getElevatorInfo(elevatorNo).response.body.items
                                            // 내부 디비에 결과 저장.
                                            val dbHelper = DBHelper(context)
                                            val sqlDB = dbHelper.writableDatabase
                                            val df = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
                                            val now = Calendar.getInstance().time
                                            val date = df.format(now)
                                            dbHelper.insertData(sqlDB, itemList[0].elevatorNo, itemList[0].buldNm, date)
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
                }
            }
        }
    }
}