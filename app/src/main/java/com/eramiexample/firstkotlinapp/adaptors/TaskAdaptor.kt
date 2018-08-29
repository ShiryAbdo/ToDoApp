package com.eramiexample.firstkotlinapp.adaptors

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.eramiexample.firstkotlinapp.R
import com.eramiexample.firstkotlinapp.activities.NavActivity
import com.eramiexample.firstkotlinapp.activities.TaskDetalisActivity
import com.eramiexample.firstkotlinapp.utilites.DbManager
import com.eramiexample.firstkotlinapp.utilites.Tasks
import kotlinx.android.synthetic.main.itm_task_row.view.*
import java.util.*
import kotlin.collections.ArrayList

class  TaskAdaptor ( val contet: Context, val listTasks:ArrayList<Tasks>, val Lisener:(Tasks)->Unit): RecyclerView.Adapter<TaskAdaptor.ViewHolder>(){
    private lateinit var mHandler: Handler
    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

        holder.bind(listTasks,listTasks.get(position),Lisener)




    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdaptor.ViewHolder {
        val rootView   =LayoutInflater.from(parent.context).inflate(R.layout.itm_task_row, parent, false)
        return ViewHolder(rootView)
     }
    override fun getItemCount(): Int {
        return listTasks.size
    }

   inner class ViewHolder (view: View) :  RecyclerView.ViewHolder(view) {


        fun bind(listTasks: ArrayList<Tasks> ,task: Tasks, listener: (Tasks)->Unit)= with(itemView) {
            cvLiner.setOnClickListener {
                val intent = Intent(contet, TaskDetalisActivity::class.java)
                intent.putExtra("task", task)
                contet.startActivity(intent)
             }
            task_name.text=task.title
            task_discription.text=task.descriptionTask
            val drawable = imgDropDownMenuIcon.getBackground() as GradientDrawable
            drawable.setColor(Color.parseColor(task.imageView))
             val rnd = Random()
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            colortext.setBackgroundColor(color)

                setOnClickListener { listener(task) }
            val bin = findViewById(R.id.bin)as ImageView
            bin.setOnClickListener {
                var dbManager= DbManager(contet)
                val selectionArgs= arrayOf(task.id.toString())
                dbManager.Delete("ID=?",selectionArgs)
                layoutPosition.also { currentPosition ->
                    listTasks.removeAt(currentPosition)
                    notifyDataSetChanged()
                }

            }
            val cb1 = findViewById (R.id.CheckBox_task) as CheckBox

            cb1.setOnCheckedChangeListener({
                buttonView, isChecked ->
                if (isChecked){
//                    mHandler = Handler()
//
//                    mHandler.postDelayed({

                        var dbManager= DbManager(contet)
                        val selectionArgs= arrayOf(task.id.toString())
                        dbManager.Delete("ID=?",selectionArgs)
                        layoutPosition.also { currentPosition ->

                            if(currentPosition<=listTasks.size){
                                listTasks.removeAt(currentPosition)
                                notifyDataSetChanged()
                            }

                        }
//
//                    }, 2000)


                }else{


                 }
            })

        }




    }






}



