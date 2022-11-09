package com.whenyourapprun.elevator

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
//import io.ktor.serialization.kotlinx.xml.*

val httpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}

val xmlHttpClient = HttpClient {
    install(ContentNegotiation) {
        // 매뉴얼 대로 추가 했으나 먹지 않음, 2022.11.08
        //xml()
    }
}

suspend fun getRocketLaunch(): RocketLaunch {
    val rockets: List<RocketLaunch> = httpClient.get("https://api.spacexdata.com/v4/launches").body()
    return rockets.last { it.launchSuccess == true }
}

suspend fun getElevatorInformation(elevatorNo: String): String {
    val elevatorServer = Environment.elevatorServer
    val getBuldElvtrList = Environment.getBuldElvtrList
    val serviceKey = Environment.serviceKey
    val url = "$elevatorServer$getBuldElvtrList?serviceKey=$serviceKey&elevator_no=$elevatorNo"
    return xmlHttpClient.get(url).body()
}