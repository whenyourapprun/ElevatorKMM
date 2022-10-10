package com.whenyourapprun.elevator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform