package com.whenyourapprun.elevator

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*

class Elevator {

    @Throws(Exception::class)
    suspend fun getElevatorInfo(elevatorNo: String): ResponseElevator {
        val elevatorInfoStr = getElevatorInformation(elevatorNo)
        return stringToResponseElevator(elevatorInfoStr)
    }

    private fun stringToResponseElevator(rawString: String): ResponseElevator {
        val responseContent = rawString.split("<response>", "</response>")[1]
        val headerContent = responseContent.split("<header>", "</header>")[1]
        val resultCode = headerContent.split("<resultCode>", "</resultCode>")[1]
        val resultMsg = headerContent.split("<resultMsg>", "</resultMsg>")[1]
        val header = Header(resultCode, resultMsg)
        val bodyContent = responseContent.split("<body>", "</body>")[1]
        val itemsContent = bodyContent.split("<items>", "</items>")[1]
        val itemArray = itemsContent.split("<item>", "</item>")
        println("itemArray ${itemArray.count()}, $itemArray")
        var itemList = mutableListOf<Item>()
        for (item in itemArray) {
            if (item.isNotEmpty()) {
                val address1 = item.split("<address1>", "</address1>")[1]
                println("address1 $address1")
                val address2 = item.split("<address2>", "</address2>")[1]
                val applcBeDt = item.split("<applcBeDt>", "</applcBeDt>")[1]
                val applcEnDt = item.split("<applcEnDt>", "</applcEnDt>")[1]
                val areaNm = item.split("<areaNm>", "</areaNm>")[1]
                val buldMgtNo1 = item.split("<buldMgtNo1>", "</buldMgtNo1>")[1]
                val buldMgtNo2 = item.split("<buldMgtNo2>", "</buldMgtNo2>")[1]
                val buldNm = item.split("<buldNm>", "</buldNm>")[1]
                val elevatorNo = item.split("<elevatorNo>", "</elevatorNo>")[1]
                val elvtrAsignNo = item.split("<elvtrAsignNo>", "</elvtrAsignNo>")[1]
                val elvtrDetailForm = item.split("<elvtrDetailForm>", "</elvtrDetailForm>")[1]
                val elvtrDivNm = item.split("<elvtrDivNm>", "</elvtrDivNm>")[1]
                val elvtrForm = item.split("<elvtrForm>", "</elvtrForm>")[1]
                val elvtrKindNm = item.split("<elvtrKindNm>", "</elvtrKindNm>")[1]
                val elvtrSttsNm = item.split("<elvtrSttsNm>", "</elvtrSttsNm>")[1]
                val frstInstallationDe = item.split("<frstInstallationDe>", "</frstInstallationDe>")[1]
                val groundFloorCnt = item.split("<groundFloorCnt>", "</groundFloorCnt>")[1]
                val installationDe = item.split("<installationDe>", "</installationDe>")[1]
                val installationPlace = item.split("<installationPlace>", "</installationPlace>")[1]
                val liveLoad = item.split("<liveLoad>", "</liveLoad>")[1]
                val ratedCap = item.split("<ratedCap>", "</ratedCap>")[1]
                val resultNm = item.split("<resultNm>", "</resultNm>")[1]
                val shuttleFloorCnt = item.split("<shuttleFloorCnt>", "</shuttleFloorCnt>")[1]
                val shuttleSection = item.split("<shuttleSection>", "</shuttleSection>")[1]
                val sigunguNm = item.split("<sigunguNm>", "</sigunguNm>")[1]
                val undgrndFloorCnt = item.split("<undgrndFloorCnt>", "</undgrndFloorCnt>")[1]
                val addItem = Item(address1, address2, applcBeDt, applcEnDt, areaNm, buldMgtNo1, buldMgtNo2, elevatorNo, elvtrAsignNo, elvtrDetailForm, elvtrDivNm, elvtrForm, elvtrKindNm, elvtrSttsNm, frstInstallationDe, groundFloorCnt, installationDe, installationPlace, liveLoad, ratedCap, resultNm, shuttleFloorCnt, shuttleSection, sigunguNm, undgrndFloorCnt)
                itemList.add(addItem)
            }
        }
        val body = Body(itemList)
        val elevatorInformation = ElevatorInformation(header, body)
        return ResponseElevator(response = elevatorInformation)
    }
}