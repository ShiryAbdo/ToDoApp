package com.eramiexample.firstkotlinapp.adaptors

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
 import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import com.amulyakhare.textdrawable.TextDrawable
import com.eramiexample.firstkotlinapp.R
import com.eramiexample.firstkotlinapp.utilites.TagsData
import java.util.*
import com.mikhaellopez.circularimageview.CircularImageView
 import kotlinx.android.synthetic.main.itmw_main_point.view.*


class  MainPointsAdaptor (val contx:Context, val listTasks:ArrayList<TagsData>, val Lisener:(TagsData)->Unit): RecyclerView.Adapter<MainPointsAdaptor.viewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.itmw_main_point, parent, false)
        return viewHolder(rootView)

    }

    override fun getItemCount(): Int {
        return listTasks.size
     }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(listTasks,listTasks.get(position),Lisener)

    }


    inner class viewHolder (view: View) :  RecyclerView.ViewHolder(view) {


        fun bind(listTasks: ArrayList<TagsData>, task: TagsData, listener: (TagsData)->Unit)= with(itemView) {
            nameSection.text= task.name
            val circularImageView = findViewById(R.id.yourCircularImageView) as CircularImageView
            val Easyy = TextDrawable.builder()
                    .beginConfig()
                    .textColor(Color.BLACK)
                     .useFont(Typeface.createFromAsset(contx.getAssets(),"fonts/gothic.ttf"))
                    .fontSize(15)
                    .withBorder(20)
                    .width(120)  // width in px
                    .height(120) // height in px
                    .endConfig()
                    .buildRoundRect("",Color.WHITE, 30)

            circularImageView.setImageDrawable(Easyy)
            circularImageView.setBorderColor(Color.parseColor(task.Color))
            circularImageView.setBorderWidth(10f)
            // Add Shadow with default param
            circularImageView.addShadow()
            // or with custom param
            circularImageView.setShadowRadius(15f)
            circularImageView.setShadowColor(Color.parseColor(task.Color))
            circularImageView.setBackgroundColor(Color.parseColor(task.Color))
            circularImageView.setShadowGravity(CircularImageView.ShadowGravity.CENTER)



        }




    }


}
