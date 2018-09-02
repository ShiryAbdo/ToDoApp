package com.eramiexample.firstkotlinapp.activities

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.eramiexample.firstkotlinapp.R
 import com.eramiexample.firstkotlinapp.adapters.CustomDropDownAdapter
import com.eramiexample.firstkotlinapp.adapters.TaskAdaptor
import com.eramiexample.firstkotlinapp.fragments.PartsFragment
import com.eramiexample.firstkotlinapp.sql.DbManager
import com.eramiexample.firstkotlinapp.model.TagsData
import com.eramiexample.firstkotlinapp.model.Tasks

import kotlinx.android.synthetic.main.activity_bas.*
import kotlinx.android.synthetic.main.add_task_row.view.*
import kotlinx.android.synthetic.main.content_bas.*
import kotlin.collections.ArrayList
import android.util.Log
import android.widget.Toast
import com.flask.colorpicker.OnColorSelectedListener
import com.flask.colorpicker.ColorPickerView
import kotlinx.android.synthetic.main.add_sction_row.view.*


class BasActivity : AppCompatActivity() {
    private val listItemsTxt = ArrayList<TagsData>()
    private var SpinnerChose: String? = null
    private var imageView: String? = null
    private  var colorRTag:String?=null
    private var spinnerAdapter: CustomDropDownAdapter?=null
    private val arrayListTasks = ArrayList<Tasks>()
    @SuppressLint("SetTextI18n")
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                titleHome.text = "My Task's"
                fab.visibility = View.VISIBLE
                fabSection.visibility = View.GONE
                fragmentManager.popBackStack()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                fabSection.visibility = View.VISIBLE
                fab.visibility = View.GONE
                titleHome.text = getString(R.string.title_notifications)
                val fragment = PartsFragment()
                val transaction = fragmentManager.beginTransaction()
                transaction.add(R.id.container, fragment) // give your fragment container id in first parameter
                transaction.addToBackStack(null)  // if written, this transaction will be added to backstack
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bas)
        setSupportActionBar(toolbar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setTitle("")
        titleHome.text = "My Task's"
        fabSection.visibility = View.GONE
        listItemsTxt.add(TagsData("Select Category", "#288a8c"))
        listItemsTxt.add(TagsData("Home", "#7b045d"))
        listItemsTxt.add(TagsData("Work", "#25833a"))
        listItemsTxt.add(TagsData("Meeting", "#0d1066"))
        listItemsTxt.add(TagsData("Personal", "#8b3a14"))
        spinnerAdapter = CustomDropDownAdapter(this@BasActivity!!, listItemsTxt)
        recyclerView.layoutManager = LinearLayoutManager(this)
        LoadQuery("%")
        LoadTags("%")




        fab.setOnClickListener { view ->
            showDialoge()


        }
        fabSection.setOnClickListener {
            addSection()

        }
    }
    private  fun LoadTags(title: String){
        var dbManager = DbManager(this)

        val projections = arrayOf("tag_name", "tag_color" )
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.QuerTage(projections, "tag_name like ?", selectionArgs, "tag_name")
        arrayListTasks.clear()
        if (cursor.moveToFirst()) {

            do {
                val tag_name = cursor.getString(cursor.getColumnIndex("tag_name"))
                val tag_color = cursor.getString(cursor.getColumnIndex("tag_color"))
                val getTaTagsDatask = TagsData(tag_name, tag_color)
                listItemsTxt.add(getTaTagsDatask)


            } while (cursor.moveToNext())
        }
        spinnerAdapter!!.notifyDataSetChanged()



    }

    private fun LoadQuery(title: String) {

        var dbManager = DbManager(this)
        val projections = arrayOf("ID", "Title", "Description", "TAG", "imageView", "date")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.Query(projections, "Title like ?", selectionArgs, "Title")
        arrayListTasks.clear()
        if (cursor.moveToFirst()) {

            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val Title = cursor.getString(cursor.getColumnIndex("Title"))
                val Description = cursor.getString(cursor.getColumnIndex("Description"))
                val TAG = cursor.getString(cursor.getColumnIndex("TAG"))
                val imageView = cursor.getString(cursor.getColumnIndex("imageView"))
                val date = cursor.getString(cursor.getColumnIndex("date"))
                val getTask = Tasks(ID, Title, Description, TAG, imageView, date)
                arrayListTasks.add(getTask)

            } while (cursor.moveToNext())
        }

        recyclerView.adapter = TaskAdaptor(this@BasActivity, arrayListTasks) {
            //                     Toast.makeText(this@BasActivity,it.title,Toast.LENGTH_LONG).show()

        }


    }

    private fun showDialoge() {
        val mDialoogeView = LayoutInflater.from(this).inflate(R.layout.add_task_row, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialoogeView).setTitle(getString(R.string.Add_new_Task))
        val mAlertDialog = mBuilder.show()
        var spinner: Spinner = mDialoogeView.findViewById(R.id.spinner) as Spinner
        spinner?.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
                SpinnerChose = listItemsTxt.get(position).name
                imageView = listItemsTxt.get(position).Color

                Toast.makeText(this@BasActivity, "on select", Toast.LENGTH_LONG).show()

            }

            override fun onNothingSelected(arg0: AdapterView<*>) =
                    Toast.makeText(this@BasActivity, getString(R.string.chose_category), Toast.LENGTH_LONG).show()


        }
        mDialoogeView.addTask.setOnClickListener() {
            if (!SpinnerChose.equals("Select Category")) {
                var dbManager = DbManager(this)
                var values = ContentValues()
                values.put("Title", mDialoogeView.taskName.text.toString())
                values.put("Description", mDialoogeView.task_discription.text.toString())
                values.put("imageView", imageView)
                values.put("TAG", SpinnerChose)
                values.put("date", "@{DateUtils.toSimpleString(journey.date)}")

                var id = dbManager.InsertToDoTask(values)
                if (id > 0) {
                    Toast.makeText(this@BasActivity, getString(R.string.task_is_add), Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@BasActivity, "not is add", Toast.LENGTH_LONG).show()

                }
                LoadQuery("%")
                mAlertDialog.dismiss()
            } else {
                Toast.makeText(this@BasActivity, "You  must select Select Category", Toast.LENGTH_LONG).show()


            }


        }


    }

    private fun addSection() {

        val mDialoogeView = LayoutInflater.from(this).inflate(R.layout.add_sction_row, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialoogeView).setTitle("Add New Section")
        val mAlertDialog = mBuilder.show()

        val colorPickerView = mDialoogeView.findViewById<View>(R.id.color_picker_view) as ColorPickerView
		colorPickerView.addOnColorChangedListener { selectedColor ->
            // Handle on color change
            Log.d("ColorPicker", "onColorChanged: 0x" + Integer.toHexString(selectedColor))
        }
        colorPickerView.addOnColorSelectedListener(object:OnColorSelectedListener {
        override fun onColorSelected(selectedColor:Int) {
            colorRTag = Integer.toHexString(selectedColor).toUpperCase()
        Toast.makeText(
        this@BasActivity,
        "selectedColor: " + Integer.toHexString(selectedColor).toUpperCase(),
        Toast.LENGTH_SHORT).show()

                }
                })

        mDialoogeView.addSection.setOnClickListener {
            if (!mDialoogeView.SectionName.equals(null)&&colorRTag!=null) {
                var dbManager = DbManager(this@BasActivity)
                var values = ContentValues()
                values.put("tag_name", mDialoogeView.SectionName.text.toString())
                values.put("tag_color","#"+colorRTag)

                var id = dbManager.InsertTagTask(values)
                if (id > 0) {
                    Toast.makeText(this@BasActivity, " is add", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@BasActivity, "Section not is add", Toast.LENGTH_LONG).show()

                }
                LoadTags("%")
                mAlertDialog.dismiss()
            } else {
                Toast.makeText(this@BasActivity, "You  must add name", Toast.LENGTH_LONG).show()


            }

        }



    }



}
