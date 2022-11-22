package com.whenyourapprun.elevator.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whenyourapprun.elevator.Item
import com.whenyourapprun.elevator.android.ui.theme.ElevatorTheme

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
                                .padding(vertical = 16.dp)
                        ) {
                            Row {
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = getString(R.string.resultNm), fontSize = 21.sp)
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = elevatorItemList[0].resultNm, fontSize = 21.sp)
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                        }
                        // 가운데
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(7f)
                        ) {
                            // 리스트 아이템으로 내용 채움
                            LazyColumn {
                                itemsIndexed(elevatorItemList) { index, item ->
                                    Box(contentAlignment = Alignment.Center) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier
                                                .padding(horizontal = 16.dp, vertical = 16.dp)
                                                .background(color = colorResource(id = R.color.white))
                                        ) {
                                            RowContent(
                                                title = getString(R.string.elevatorNo),
                                                content = item.elevatorNo
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.elvtrAsignNo),
                                                content = item.elvtrAsignNo
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.areaNm),
                                                content = item.areaNm
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.sigunguNm),
                                                content = item.sigunguNm
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.address1),
                                                content = item.address1
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.address2),
                                                content = item.address2
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.buldNm),
                                                content = item.buldNm
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.buldMgtNo1),
                                                content = item.buldMgtNo1
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.buldMgtNo2),
                                                content = item.buldMgtNo2
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.resultNm),
                                                content = item.resultNm
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.applcBeDt),
                                                content = item.applcBeDt
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.applcEnDt),
                                                content = item.applcEnDt
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.elvtrDivNm),
                                                content = item.elvtrDivNm
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.elvtrForm),
                                                content = item.elvtrForm
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.elvtrKindNm),
                                                content = item.elvtrKindNm
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.elvtrDetailForm),
                                                content = item.elvtrDetailForm
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.elvtrSttsNm),
                                                content = item.elvtrSttsNm
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.frstInstallationDe),
                                                content = item.frstInstallationDe
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.installationDe),
                                                content = item.installationDe
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.installationPlace),
                                                content = item.installationPlace
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.liveLoad),
                                                content = item.liveLoad
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.ratedCap),
                                                content = item.ratedCap
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.shuttleFloorCnt),
                                                content = item.shuttleFloorCnt
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.shuttleSection),
                                                content = item.shuttleSection
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.groundFloorCnt),
                                                content = item.groundFloorCnt
                                            )
                                            Divider()
                                            RowContent(
                                                title = getString(R.string.undgrndFloorCnt),
                                                content = item.undgrndFloorCnt
                                            )
                                        }
                                    }
                                }
                            }
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
                                Text(stringResource(id = R.string.OK))
                            }
                        }
                    }
                } // end_Scaffold
            } // end_ElevatorTheme
        } // end_setContent
    } // end_onCreate

} // end_ElevatorResultActivity

@Composable
fun RowContent(title: String, content: String) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.not_selected))
            .padding(vertical = 16.dp)
    )
    Text(
        text = content,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    )
}
