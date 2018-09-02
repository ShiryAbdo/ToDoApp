package com.eramiexample.firstkotlinapp.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import android.widget.Toast
import com.eramiexample.firstkotlinapp.model.User
import java.util.*


class  DbManager {


    // create table sql query
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")

    // drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

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
            p0!!.execSQL(CREATE_USER_TABLE)
            p0!!.execSQL(sqlCreateTable)
            p0!!.execSQL(CREATE_TABLE_TAG);
            p0!!.execSQL(CREATE_TABLE_TODO_TAG);
            Toast.makeText(this.context," database is created", Toast.LENGTH_LONG).show()

        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table IF EXISTS " + dbTable)
            p0.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
            p0.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_TAG);
            p0.execSQL(DROP_USER_TABLE)

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


        /**
         * This method is to fetch all user and return the list of user records
         *
         * @return list
         */
        fun getAllUser(): List<User> {

            // array of columns to fetch
            val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD)

            // sorting orders
            val sortOrder = "$COLUMN_USER_NAME ASC"
            val userList = ArrayList<User>()

            val db = this.readableDatabase

            // query the user table
            val cursor = db.query(TABLE_USER, //Table to query
                    columns,            //columns to return
                    null,     //columns for the WHERE clause
                    null,  //The values for the WHERE clause
                    null,      //group the rows
                    null,       //filter by row groups
                    sortOrder)         //The sort order
            if (cursor.moveToFirst()) {
                do {
                    val user = User(id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                            name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                            email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                            password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)))

                    userList.add(user)
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return userList
        }


        /**
         * This method is to create user record
         *
         * @param user
         */
        fun addUser(user: User) {
            val db = this.writableDatabase

            val values = ContentValues()
            values.put(COLUMN_USER_NAME, user.name)
            values.put(COLUMN_USER_EMAIL, user.email)
            values.put(COLUMN_USER_PASSWORD, user.password)

            // Inserting Row
            db.insert(TABLE_USER, null, values)
            db.close()
        }

        /**
         * This method to update user record
         *
         * @param user
         */
        fun updateUser(user: User) {
            val db = this.writableDatabase

            val values = ContentValues()
            values.put(COLUMN_USER_NAME, user.name)
            values.put(COLUMN_USER_EMAIL, user.email)
            values.put(COLUMN_USER_PASSWORD, user.password)

            // updating row
            db.update(TABLE_USER, values, "$COLUMN_USER_ID = ?",
                    arrayOf(user.id.toString()))
            db.close()
        }

        /**
         * This method is to delete user record
         *
         * @param user
         */
        fun deleteUser(user: User) {

            val db = this.writableDatabase
            // delete user record by id
            db.delete(TABLE_USER, "$COLUMN_USER_ID = ?",
                    arrayOf(user.id.toString()))
            db.close()


        }

        /**
         * This method to check user exist or not
         *
         * @param email
         * @return true/false
         */
        fun checkUser(email: String): Boolean {

            // array of columns to fetch
            val columns = arrayOf(COLUMN_USER_ID)
            val db = this.readableDatabase

            // selection criteria
            val selection = "$COLUMN_USER_EMAIL = ?"

            // selection argument
            val selectionArgs = arrayOf(email)

            // query user table with condition
            /**
             * Here query function is used to fetch records from user table this function works like we use sql query.
             * SQL query equivalent to this query function is
             * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
             */
            val cursor = db.query(TABLE_USER, //Table to query
                    columns,        //columns to return
                    selection,      //columns for the WHERE clause
                    selectionArgs,  //The values for the WHERE clause
                    null,  //group the rows
                    null,   //filter by row groups
                    null)  //The sort order


            val cursorCount = cursor.count
            cursor.close()
            db.close()

            if (cursorCount > 0) {
                return true
            }

            return false
        }

        /**
         * This method to check user exist or not
         *
         * @param email
         * @param password
         * @return true/false
         */
        fun checkUser(email: String, password: String): Boolean {

            // array of columns to fetch
            val columns = arrayOf(COLUMN_USER_ID)

            val db = this.readableDatabase

            // selection criteria
            val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"

            // selection arguments
            val selectionArgs = arrayOf(email, password)

            // query user table with conditions
            /**
             * Here query function is used to fetch records from user table this function works like we use sql query.
             * SQL query equivalent to this query function is
             * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
             */
            val cursor = db.query(TABLE_USER, //Table to query
                    columns, //columns to return
                    selection, //columns for the WHERE clause
                    selectionArgs, //The values for the WHERE clause
                    null,  //group the rows
                    null, //filter by row groups
                    null) //The sort order

            val cursorCount = cursor.count
            cursor.close()
            db.close()

            if (cursorCount > 0)
                return true

            return false

        }




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


    companion object {

        // Database Version
        private val DATABASE_VERSION = 1

        // Database Name
        private val DATABASE_NAME = "UserManager.db"

        // User table name
        private val TABLE_USER = "user"

        // User Table Columns names
        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_EMAIL = "user_email"
        private val COLUMN_USER_PASSWORD = "user_password"
    }




}

