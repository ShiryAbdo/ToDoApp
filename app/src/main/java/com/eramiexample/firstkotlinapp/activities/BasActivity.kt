package com.eramiexample.firstkotlinapp.activities

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.eramiexample.firstkotlinapp.R
 import com.eramiexample.firstkotlinapp.adaptors.CustomDropDownAdapter
import com.eramiexample.firstkotlinapp.adaptors.TaskAdaptor
import com.eramiexample.firstkotlinapp.fragments.PartsFragment
import com.eramiexample.firstkotlinapp.utilites.DbManager
import com.eramiexample.firstkotlinapp.utilites.TagsData
import com.eramiexample.firstkotlinapp.utilites.Tasks

import kotlinx.android.synthetic.main.activity_bas.*
import kotlinx.android.synthetic.main.add_task_row.view.*
import kotlinx.android.synthetic.main.content_bas.*
import java.util.*
import kotlin.collections.ArrayList

class BasActivity : AppCompatActivity() {
    var id =0L
    val listItemsTxt = ArrayList<TagsData>( )
    var SpinnerChose :String?=null
    var cancel = false
    var imageView:Int?=null
    val arrayListTasks= ArrayList<Tasks>()

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

        listItemsTxt.add(TagsData("Home",R.drawable.ic_home_icon_silhouette))
        listItemsTxt.add(TagsData("Work",R.drawable.ic_work_briefcase))
        listItemsTxt.add(TagsData("Meeting",R.drawable.ic_meeting))
        listItemsTxt.add(TagsData("Personal",R.drawable.ic_man_user))





//         arrayListTasks.add(task)
        recyclerView.layoutManager= LinearLayoutManager(this)
        LoadQuery("%")
//        recyclerView.adapter= TaskAdaptor(arrayListTasks){
//           Toast.makeText(this@BasActivity,"${it.title} "+ "clicked",Toast.LENGTH_LONG).show()
//        }


        fab.setOnClickListener { view ->
            val mDialoogeView= LayoutInflater.from(this).inflate(R.layout.add_task_row,null)
            val mBuilder=AlertDialog.Builder(this)
                    .setView(mDialoogeView)
                    .setTitle("Add  new Task")
            val  mAlertDialog= mBuilder.show()
//            var taskName=    mDialoogeView.taskName.text.toString()
//            var taskDiscription =mDialoogeView.task_discription.text.toString()
            var spinnerAdapter: CustomDropDownAdapter = CustomDropDownAdapter(this@BasActivity!!, listItemsTxt)
            var spinner: Spinner = mDialoogeView.findViewById(R.id.spinner) as Spinner
            spinner?.adapter = spinnerAdapter
             spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                //Performing action onItemSelected and onNothing selected
                override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
                    SpinnerChose =listItemsTxt.get(position).name
                    imageView=listItemsTxt.get(position).imageView



                }

                override fun onNothingSelected(arg0: AdapterView<*>) {
                    // TODO: Auto-generated method stub


                }

            }
            mDialoogeView.addTask.setOnClickListener(){
                var dbManager = DbManager(this)
                var values= ContentValues()
                val path = Uri.parse("android.resource://com.segf4ult.test/" + imageView)
                var pah = path.toString()
                values.put("Title",mDialoogeView.taskName.text.toString())
                values.put("Description", mDialoogeView.task_discription.text.toString())
                values.put("TAG",SpinnerChose)
                values.put("imageView",pah)
                var gg="@{DateUtils.toSimpleString(journey.date)}"
                values.put("date","@{DateUtils.toSimpleString(journey.date)}")

                var id=  dbManager.Insert(values)
                if(id>0){
                    Toast.makeText(this@BasActivity,"task is add",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@BasActivity,"not is add",Toast.LENGTH_LONG).show()

                }
                LoadQuery("%")


//                    val getTask= Tasks(id++,  mDialoogeView.taskName.text.toString(),  mDialoogeView.task_discription.text.toString(),
//                            SpinnerChose+"", this!!.imageView!!, "")
//                    arrayListTasks.add(getTask)
//                    recyclerView.adapter.notifyDataSetChanged()
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
