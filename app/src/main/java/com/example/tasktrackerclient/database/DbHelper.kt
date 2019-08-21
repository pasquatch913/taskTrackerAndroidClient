package com.example.tasktrackerclient.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, "${TABLE_NAME}.db", null, TABLE_VERSION) {

    companion object {
        const val TABLE_NAME = "Tasks"
        const val TABLE_VERSION = 2

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_COMP_GOAL = "completions_goal"
        const val COLUMN_COMPS = "completions"
        const val COLUMN_WEIGHT = "weight"
        const val COLUMN_DUE_DATE = "due_date"
        const val COLUMN_ACTIVE = "active"
        const val COLUMN_RECURRING = "recurring"
    }

    private var db: SQLiteDatabase? = null

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_COMP_GOAL INTEGER," +
                "$COLUMN_COMPS INTEGER," +
                "$COLUMN_WEIGHT INTEGER," +
                "$COLUMN_DUE_DATE TEXT," +
                "$COLUMN_ACTIVE TEXT," +
                "$COLUMN_RECURRING TEXT);"
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
}