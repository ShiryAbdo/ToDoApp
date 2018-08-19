package com.eramiexample.firstkotlinapp.activities

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.eramiexample.firstkotlinapp.R
 import com.eramiexample.firstkotlinapp.adaptors.CustomDropDownAdapter
import com.eramiexample.firstkotlinapp.adaptors.TaskAdaptor
import com.eramiexample.firstkotlinapp.fragments.PartsFragment
import com.eramiexample.firstkotlinapp.utilites.DbManager
import com.eramiexample.firstkotlinapp.utilites.TagsData
import com.eramiexample.firstkotlinapp.utilites.Tasks

import kotlinx.android.synthetic.main.activity_bas.*
import kotlinx.android.synthetic.main.add_task_row.*
import kotlinx.android.synthetic.main.add_task_row.view.*
import kotlinx.android.synthetic.main.content_bas.*
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth



class BasActivity : AppCompatActivity() {
    private var id =0L
    private val listItemsTxt = ArrayList<TagsData>( )
    private var SpinnerChose :String?=null
    private var cancel = false
    private var imageView:String?=null
    private val arrayListTasks= ArrayList<Tasks>()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                 return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                 return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                val fragment = PartsFragment()
                val transaction = fragmentManager.beginTransaction()
                transaction.add(R.id.container,fragment) // give your fragment container id in first parameter
                transaction.addToBackStack(null)  // if written, this transaction will be added to backstack
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bas)
        setSupportActionBar(toolbar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setTitle("")
        listItemsTxt.add(TagsData("Select Catogiry","#ffffff"))
        listItemsTxt.add(TagsData("Home","#7b045d"))
        listItemsTxt.add(TagsData("Work","#25833a"))
        listItemsTxt.add(TagsData("Meeting","#0d1066"))
        listItemsTxt.add(TagsData("Personal","#8b3a14"))
        recyclerView.layoutManager= LinearLayoutManager(this)
        LoadQuery("%")



        fab.setOnClickListener { view ->
            val mDialoogeView= LayoutInflater.from(this).inflate(R.layout.add_task_row,null)
            val mBuilder=AlertDialog.Builder(this).setView(mDialoogeView).setTitle(getString(R.string.Add_new_Task))
            val  mAlertDialog= mBuilder.show()
            var spinnerAdapter: CustomDropDownAdapter = CustomDropDownAdapter(this@BasActivity!!, listItemsTxt)
            var spinner: Spinner = mDialoogeView.findViewById(R.id.spinner) as Spinner
            spinner?.adapter = spinnerAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
                    SpinnerChose =listItemsTxt.get(position).name
                    imageView=listItemsTxt.get(position).Color

                    Toast.makeText(this@BasActivity,"on select",Toast.LENGTH_LONG).show()

                }
                override fun onNothingSelected(arg0: AdapterView<*>) =
                        Toast.makeText(this@BasActivity,getString(R.string.chose_category),Toast.LENGTH_LONG).show()


            }
            mDialoogeView.addTask.setOnClickListener(){

                var dbManager = DbManager(this)
                var values= ContentValues()
                values.put("Title",mDialoogeView.taskName.text.toString())
                values.put("Description", mDialoogeView.task_discription.text.toString())
                values.put("imageView", imageView)
                values.put("TAG",SpinnerChose)
                values.put("date","@{DateUtils.toSimpleString(journey.date)}")

                var id=  dbManager.Insert(values)
                if(id>0){
                    Toast.makeText(this@BasActivity,getString(R.string.task_is_add),Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@BasActivity,"not is add",Toast.LENGTH_LONG).show()

                }
                LoadQuery("%")
                mAlertDialog.dismiss()

            }


        }
    }

    fun LoadQuery(title:String){



        var dbManager=DbManager(this)
        val projections= arrayOf("ID","Title","Description","TAG","imageView","date")
        val selectionArgs= arrayOf(title)
        val cursor=dbManager.Query(projections,"Title like ?",selectionArgs,"Title")
        arrayListTasks.clear()
        if(cursor.moveToFirst()){

            do{
                val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                val Title=cursor.getString(cursor.getColumnIndex("Title"))
                val Description=cursor.getString(cursor.getColumnIndex("Description"))
                val TAG=cursor.getString(cursor.getColumnIndex("TAG"))
                val imageView=cursor.getString(cursor.getColumnIndex("imageView"))
                val date=cursor.getString(cursor.getColumnIndex("date"))
                val getTask= Tasks(ID, Title, Description, TAG,imageView, date)
                arrayListTasks.add(getTask)

            }while (cursor.moveToNext())
        }

                 recyclerView.adapter= TaskAdaptor(arrayListTasks){

                }


    }


}
