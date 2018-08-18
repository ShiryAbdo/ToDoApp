package com.eramiexample.firstkotlinapp.adaptors

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.eramiexample.firstkotlinapp.R
import com.eramiexample.firstkotlinapp.utilites.Tasks
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.itm_task_row.view.*
import java.util.*

class  TaskAdaptor (val listTasks:List<Tasks> ,val Lisener:(Tasks)->Unit): RecyclerView.Adapter<TaskAdaptor.ViewHolder>(){


    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

        holder.bind(listTasks.get(position),Lisener)
      }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdaptor.ViewHolder {
        val rootView =LayoutInflater.from(parent.context).inflate(R.layout.itm_task_row, parent, false)
        return ViewHolder(rootView)
     }

    override fun getItemCount(): Int {
        return listTasks.size
     }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(task: Tasks, listener: (Tasks)->Unit)= with(itemView) {
            task_name.text=task.title
            task_discription.text=task.descriptionTask
            Picasso.with(context).load(task.imageView).into(imageType);
//            imageType.setImageResource(task.imageView)
            val rnd = Random()
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            colortext.setBackgroundColor(color)
            val cb1 = findViewById (R.id.CheckBox_task) as CheckBox
            setOnClickListener { listener(task) }


        }

    }

}