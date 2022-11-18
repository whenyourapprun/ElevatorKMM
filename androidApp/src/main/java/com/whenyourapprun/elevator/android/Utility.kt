package com.whenyourapprun.elevator.android

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import java.util.*

class Utility {
    companion object {
        private const val SHARED_NAME = "ElevatorConfig"
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

    fun objectIdToDate(id: String): Date {
        val hex = id.substring(0, 8)
        val x = hex.toLong(16) * 1000
        return Date(x)
    }

    fun dpToPx(dp: Int, displayMetrics: DisplayMetrics): Int {
        return (dp * displayMetrics.density).toInt()
    }

    fun pxToDp(px: Int, displayMetrics: DisplayMetrics): Int {
        return (px / displayMetrics.density).toInt()
    }

    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = wm.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = wm.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.height() - insets.bottom - insets.top
        } else {
            val displayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }

}