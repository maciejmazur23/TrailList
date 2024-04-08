package edu.put.kotlindetailslist.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import edu.put.kotlindetailslist.R


class DBHandler(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "trails.db"
        const val TABLE_TRAIL = "TRAILS"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_LENGTH = "length"
        const val COLUMN_DIFFICULTY = "difficulty"
        const val COLUMN_ESTIMATED_DURATION = "estimated_duration"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IMAGE = "image"
    }

    private val CREATE_TRAIL_TABLE = ("CREATE TABLE " + TABLE_TRAIL + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_LENGTH + " INTEGER, " +
            COLUMN_DIFFICULTY + " TEXT, " +
            COLUMN_ESTIMATED_DURATION + " INTEGER, " +
            COLUMN_DESCRIPTION + " TEXT, "+
            COLUMN_IMAGE + " INTEGER"+
            ");")

    private val DROP_IF_EXIST = "DROP TABLE IF EXISTS $TABLE_TRAIL"


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TRAIL_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_IF_EXIST)
        onCreate(db)
    }

    fun createTrialTable() {
        val db = this.writableDatabase
        db.execSQL(DROP_IF_EXIST)
        db.execSQL(CREATE_TRAIL_TABLE)
    }

    fun dropTrialTable() {
        val db = this.writableDatabase
        db.execSQL(DROP_IF_EXIST)
        onCreate(db)
    }

    fun findAll(): List<Trail> {
        val query = "SELECT * FROM $TABLE_TRAIL"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        cursor.moveToFirst()
        val list = generateSequence {
            val next = cursor.moveToNext()
            getTrailFromCursor(cursor, next)
        }.take(cursor.count).toList()

        db.close()
        return list
    }

    private fun getTrailFromCursor(cursor: Cursor, next: Boolean): Trail? {
        if (next) {
            val id = Integer.parseInt(cursor.getString(0))
            val name = cursor.getString(1)
            val length = cursor.getInt(2)
            val difficulty = cursor.getString(3)
            val estimatedDuration = cursor.getInt(4)
            val description = cursor.getString(5)
            val image = cursor.getInt(6)

            return Trail(id, name, length, difficulty, estimatedDuration, description, image)
        }
        return null
    }

    fun addTrail(trail: Trail) {
        val values = ContentValues()
        values.put(COLUMN_NAME, trail.name)
        values.put(COLUMN_LENGTH, trail.length)
        values.put(COLUMN_DIFFICULTY, trail.difficulty)
        values.put(COLUMN_ESTIMATED_DURATION, trail.estimatedDuration)
        values.put(COLUMN_DESCRIPTION, trail.description)
        values.put(COLUMN_IMAGE, trail.image)

        val db = this.writableDatabase
        db.insertOrThrow(TABLE_TRAIL, null, values)
        db.close()
    }

}