package com.eramiexample.firstkotlinapp.utilites

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import android.widget.Toast
import java.util.*


class  DbManager {


    // Database Version
    private val DATABASE_VERSION = 1

    // Database Name
    private val DATABASE_NAME = "contactsManager"

    // Table Names
//    private val TABLE_TODO = "todos"
    private val TABLE_TAG = "tags"
    private val TABLE_TODO_TAG = "todo_tags"

//
    val dbName = "MyNotes"
    val dbTable = "Notes"

    // NOTES Table - column nmaes
    val colID = "ID"
    val colTitle = "Title"
    val colDes = "Description"
    val tag ="TAG"
    val imageView="imageView"
    val createdADate =  "date"
    val dbVersion = 1

    // TAGS Table - column names
    private val KEY_TAG_NAME = "tag_name"
    private val KEY_TAG_COLOR = "tag_color"

    // NOTE_TAGS Table - column names
    private val KEY_TODO_ID = "todo_id"
    private val KEY_TAG_ID = "tag_id"
    //CREATE TABLE IF NOT EXISTS MyNotes (ID INTEGER PRIMARY KEY,title TEXT, Description TEXT);"
    // Table Create Statements
    // Todo table create statement

    val sqlCreateTable =
            "CREATE TABLE IF NOT EXISTS " + dbTable + " (" + colID + " INTEGER PRIMARY KEY," + colTitle +
                    " TEXT, " + colDes + " TEXT, " + tag + " TEXT, " + imageView + " TEXT, " + createdADate + " TEXT);"

    // Tag table create statement
    private val CREATE_TABLE_TAG = ("CREATE TABLE " + TABLE_TAG
            + "(" + colID + " INTEGER PRIMARY KEY," + KEY_TAG_NAME + " TEXT,"
            + KEY_TAG_COLOR + " TEXT" + ")")

    // todo_tag table create statement
    private val CREATE_TABLE_TODO_TAG = ("CREATE TABLE "
            + TABLE_TODO_TAG + "(" + colID + " INTEGER PRIMARY KEY,"
            + KEY_TODO_ID + " INTEGER," + KEY_TAG_ID + " INTEGER,"
            + createdADate + " TEXT" + ")")
    var sqlDB: SQLiteDatabase? = null

    constructor(context:Context){
        var db=DatabaseHelperNotes(context)
        sqlDB=db.writableDatabase

    }


    inner class  DatabaseHelperNotes: SQLiteOpenHelper {
        var context:Context?=null
        constructor(context:Context):super(context,dbName,null,dbVersion){
            this.context=context
        }
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            p0!!.execSQL(CREATE_TABLE_TAG);
            p0!!.execSQL(CREATE_TABLE_TODO_TAG);
            Toast.makeText(this.context," database is created", Toast.LENGTH_LONG).show()

        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table IF EXISTS " + dbTable)
            p0.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
            p0.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_TAG);
        }


//        fun getdata(): ByteArray {
//            val db = writableDatabase
//            val res = db.rawQuery("select * from " + TABLE_NAME, null)
//
//            if (res.moveToFirst()) {
//                do {
//                    return res.getBlob(0)
//                } while (res.moveToNext())
//            }
//            return byteArrayOf()
//        }




    }

    fun InsertToDoTask(values:ContentValues):Long{

        val ID= sqlDB!!.insert(dbTable,"",values)
        return ID
    }
    fun InsertTagTask(values:ContentValues):Long{

        val ID= sqlDB!!.insert(TABLE_TAG,"",values)
        return ID
    }

    fun  Query(projection:Array<String>,selection:String,selectionArgs:Array<String>,sorOrder:String): Cursor {

        val qb= SQLiteQueryBuilder()
        qb.tables=dbTable
        val cursor=qb.query(sqlDB,projection,selection,selectionArgs,null,null,sorOrder)
        return cursor
    }
    fun QuerCatoager (projection:Array<String>,selection:String,selectionArgs:Array<String>,sorOrder:String): Cursor{
        val qb= SQLiteQueryBuilder()
        qb.tables=dbTable
        val cursor=qb.query(sqlDB,projection,selection,selectionArgs,null,null,sorOrder)
        return cursor

    }
    fun Delete(selection:String,selectionArgs:Array<String>):Int{

        val count=sqlDB!!.delete(dbTable,selection,selectionArgs)
        return  count
    }

    fun Update(values:ContentValues,selection:String,selectionargs:Array<String>):Int{

        val count=sqlDB!!.update(dbTable,values,selection,selectionargs)
        return count
    }

    fun QuerTage (projection:Array<String>,selection:String,selectionArgs:Array<String>,sorOrder:String): Cursor{
        val qb= SQLiteQueryBuilder()
        qb.tables=TABLE_TAG
        val cursor=qb.query(sqlDB,projection,selection,selectionArgs,null,null,sorOrder)
        return cursor

    }


}

