package com.example.diary

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){

        companion object {
            private const val DB_NAME = "diary.sqlite"
            private const val DB_VERSION = 1
        }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.let {
            it.execSQL("CREATE TABLE items(" + "diary_date TEXT PRIMARY KEY, diary_text TEXT)")

            it.execSQL("INSERT INTO items(diary_date, diary_text)" + "VALUES('2024/01/01', 'テスト１')")
            it.execSQL("INSERT INTO items(diary_date, diary_text)" + "VALUES('2024/02/01', 'テスト２')")
            it.execSQL("INSERT INTO items(diary_date, diary_text)" + "VALUES('2024/03/01', 'テスト３')")
            it.execSQL("INSERT INTO items(diary_date, diary_text)" + "VALUES('2024/04/01', 'テスト４')")
            it.execSQL("INSERT INTO items(diary_date, diary_text)" + "VALUES('2024/05/01', 'テスト５')")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}