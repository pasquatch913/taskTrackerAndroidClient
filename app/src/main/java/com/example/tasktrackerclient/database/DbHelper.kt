package com.example.tasktrackerclient.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, "${TABLE_NAME}.db", null, TABLE_VERSION) {

    companion object {
        const val TABLE_NAME = "Tasks"
        const val TABLE_VERSION = 1

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }

    private var db: SQLiteDatabase? = null

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME TEXT);"
        db.execSQL(sql)
    }

    fun getDb(): SQLiteDatabase? {
        if (null == db || db !!.isOpen.not())
            db = this.writableDatabase
        return db
    }

    @Synchronized
    override fun close() {
        db?.close()
        super.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //ignore
    }
}