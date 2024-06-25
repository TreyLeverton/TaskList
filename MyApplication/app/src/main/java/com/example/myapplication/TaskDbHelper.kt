//Task database helper function
package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TaskDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "tasks.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_TASKS = "tasks"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_COMPLETED = "completed"

        private const val TABLE_CREATE = """
            CREATE TABLE $TABLE_TASKS (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
            $COLUMN_NAME TEXT, 
            $COLUMN_COMPLETED INTEGER);
        """
    }
}
