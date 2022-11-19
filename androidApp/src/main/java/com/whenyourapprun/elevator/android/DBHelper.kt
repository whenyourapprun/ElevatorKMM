package com.whenyourapprun.elevator.android

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase

class DBHelper(context: Context?) : SQLiteOpenHelper(context, "Elevator.sqlite", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS playTable (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, number TEXT, build TEXT, date TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS playTable;")
        onCreate(db)
    }

    fun insertData(db: SQLiteDatabase, number: String, build: String, date: String) {
        db.execSQL("INSERT INTO playTable VALUES (null, '$number', '$build', '$date');")
    }

}