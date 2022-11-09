package com.whenyourapprun.elevator

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketLaunch (
    @SerialName("flight_number")
    val flightNumber: Int,
    @SerialName("name")
    val missionName: String,
    @SerialName("date_utc")
    val launchDateUTC: String,
    @SerialName("success")
    val launchSuccess: Boolean?,
)

data class ResponseElevator(
    val response: ElevatorInformation
)

data class ElevatorInformation(
    val header: Header,
    val body: Body
)

data class Header(
    val resultCode: String,
    val resultMsg: String
)

data class Body(
    val items: List<Item>
)

data class Item(
    val address1: String,
    val address2: String,
    val applcBeDt: String,
    val applcEnDt: String,
    val areaNm: String,
    val buldMgtNo1: String,
    val buldMgtNo2: String,
    val elevatorNo: String,
    val elvtrAsignNo: String,
    val elvtrDetailForm: String,
    val elvtrDivNm: String,
    val elvtrForm: String,
    val elvtrKindNm: String,
    val elvtrSttsNm: String,
    val frstInstallationDe: String,
    val groundFloorCnt: String,
    val installationDe: String,
    val installationPlace: String,
    val liveLoad: String,
    val ratedCap: String,
    val resultNm: String,
    val shuttleFloorCnt: String,
    val shuttleSection: String,
    val sigunguNm: String,
    val undgrndFloorCnt: String
)