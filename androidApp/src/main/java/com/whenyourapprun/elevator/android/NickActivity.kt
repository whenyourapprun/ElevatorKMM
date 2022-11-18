package com.whenyourapprun.elevator.android

import android.content.Intent
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.whenyourapprun.elevator.android.ui.theme.ElevatorTheme
import kotlinx.coroutines.launch

class NickActivity : ComponentActivity() {
    companion object {
        private const val TAG = "NickActivity"
    }
    private val util = Utility()

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElevatorTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val (text, setValue) = remember { mutableStateOf("") }
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
                                    value = text,
                                    onValueChange = setValue,
                                    label = { Text(stringResource(id = R.string.NickInput)) },
                                    placeholder = { Text(text = util.getNick(context = context)) },
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                    keyboardActions = KeyboardActions(
                                        onDone = { keyboardController?.hide() }
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
                                            scaffoldState.snackbarHostState.showSnackbar("input nick $text")
                                        }
                                        // 로그 찍어보자
                                        Log.d(TAG, "input nick $text")
                                        // */
                                        if (text != "") {
                                            util.setNick(context = context, text)
                                        }
                                        // 메인 창으로 이동
                                        val intent = Intent(context, MainActivity::class.java)
                                        context.startActivity(intent)
                                        // 뒤로 가기 했을 때 나오지 않도록 종료
                                        finish()
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
