package com.whenyourapprun.elevator.android

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.whenyourapprun.elevator.Elevator
import com.whenyourapprun.elevator.Item
import org.json.JSONArray
import java.util.*


class Utility {
    companion object {
        private const val SHARED_NAME = "ElevatorConfig"
    }

    fun getUUID(context: Context): String {
        val sharedPref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString("uuid", "").toString()
    }
    fun setUUID(context: Context, uuid: String) {
        val sharedPref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("uuid", uuid)
        editor.commit()
    }

    fun getNick(context: Context): String {
        val sharedPref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString("nick", "").toString()
    }
    fun setNick(context: Context, nick: String) {
        val sharedPref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("nick", nick)
        editor.commit()
    }

    fun getElevatorItems(context: Context): List<Item> {
        val sharedPref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        val json = sharedPref.getString("itemList", null)

        val storedData: List<Item> = Gson().fromJson(json, object : TypeToken<List<Item?>>() {}.type
        )

        return storedData
    }

    fun setElevatorItems(context: Context, itemList: List<Item>) {
        val json = Gson().toJson(itemList)
        val sharedPref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("itemList", json)
        editor.apply()
    }

    fun objectIdToDate(id: String): Date {
        val hex = id.substring(0, 8)
        val x = hex.toLong(16) * 1000
        return Date(x)
    }
}

// Compose 에서 해당 Activity 찾을 때 사용하는 함수
fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}