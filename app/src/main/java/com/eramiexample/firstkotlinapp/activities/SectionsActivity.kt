package com.eramiexample.firstkotlinapp.activities

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.widget.Toast
import com.eramiexample.firstkotlinapp.R
import com.eramiexample.firstkotlinapp.adapters.TaskAdaptor
import com.eramiexample.firstkotlinapp.sql.DbManager
import com.eramiexample.firstkotlinapp.model.Tasks
import kotlinx.android.synthetic.main.activity_sections.*
import kotlinx.android.synthetic.main.add_task_row_sections.view.*
import kotlinx.android.synthetic.main.content_bas.*

class SectionsActivity : AppCompatActivity() {
    private val arrayListTasks= ArrayList<Tasks>()
    private var titleName:String?=null
    private  var ColorTage:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sections)
        setSupportActionBar(toolbar)
        titleName=getIntent().getStringExtra("Tag")
        ColorTage=getIntent().getStringExtra("ColorTage")
        title=""
        titleTaskSetion.text=  titleName + " Tasks"
        backROW.setOnClickListener {
            val intent = Intent(this@SectionsActivity, BasActivity::class.java)
            startActivity(intent)
            finish()
        }
        recyclerView.layoutManager= LinearLayoutManager(this)
        if(titleName!=null){
            selectCatogory(titleName!!)
        }

        fab.setOnClickListener {
            showDialoge()
        }
    }

    private  fun selectCatogory(catogory:String){
        val dbManager= DbManager(this@SectionsActivity)
        val projections= arrayOf("ID","Title","Description","TAG","imageView","date")
        val selectionArgs= arrayOf(catogory)
        val cursor=dbManager.QuerCatoager(projections,"TAG like ?",selectionArgs,"TAG")
        arrayListTasks.clear()
        if(cursor.moveToFirst()){

            do{
                val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                val Title=cursor.getString(cursor.getColumnIndex("Title"))
                val Description=cursor.getString(cursor.getColumnIndex("Description"))
                val TAG=cursor.getString(cursor.getColumnIndex("TAG"))
                val imageView=cursor.getString(cursor.getColumnIndex("imageView"))
                val date=cursor.getString(cursor.getColumnIndex("date"))
                val getTask= Tasks(ID, Title, Description, TAG, imageView, date)
                arrayListTasks.add(getTask)

            }while (cursor.moveToNext())
        }

        recyclerView.adapter= TaskAdaptor(this@SectionsActivity, arrayListTasks) {

        }

    }

    private  fun showDialoge (){
        val mDialoogeView= LayoutInflater.from(this).inflate(R.layout.add_task_row_sections,null)
        val mBuilder= AlertDialog.Builder(this).setView(mDialoogeView).setTitle(getString(R.string.Add_new_Task))
        val  mAlertDialog= mBuilder.show()
        val drawable = mDialoogeView.ColorIcon.getBackground() as GradientDrawable
        drawable.setColor(Color.parseColor(ColorTage))



        mDialoogeView.addTaskSection.setOnClickListener(){
            if(!titleName.equals("Select Category")){
                var dbManager = DbManager(this)
                var values= ContentValues()
                values.put("Title",mDialoogeView.taskNameA.text.toString())
                values.put("Description", mDialoogeView.task_discriptio.text.toString())
                values.put("imageView", ColorTage)
                values.put("TAG",titleName)
                values.put("date","@{DateUtils.toSimpleString(journey.date)}")

                var id=  dbManager.InsertToDoTask(values)
                if(id>0){
                    Toast.makeText(this@SectionsActivity,getString(R.string.task_is_add), Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@SectionsActivity,"not is add", Toast.LENGTH_LONG).show()

                }
                selectCatogory(titleName!!)
                mAlertDialog.dismiss()
            }else{
                Toast.makeText(this@SectionsActivity,"You  must select Select Category", Toast.LENGTH_LONG).show()


            }


        }






    }

}
