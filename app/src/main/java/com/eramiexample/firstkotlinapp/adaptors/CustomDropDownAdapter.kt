package com.eramiexample.firstkotlinapp.adaptors

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.eramiexample.firstkotlinapp.R
import com.eramiexample.firstkotlinapp.utilites.TagsData
import android.graphics.drawable.GradientDrawable
import android.support.design.widget.FloatingActionButton
import android.widget.Toast
import kotlinx.android.synthetic.main.view_drop_down_menu.view.*


class CustomDropDownAdapter(val context: Context, var listItemsTxt: ArrayList<TagsData>) : BaseAdapter() {


    val mInflater: LayoutInflater = LayoutInflater.from(context)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.view_drop_down_menu, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }
        val params = view.layoutParams
        params.height = 60
        view.layoutParams = params

        vh.label.text = listItemsTxt.get(position).name
        val drawable = vh.imgDropDownMenuIcon.getBackground() as GradientDrawable
        drawable.setColor(Color.parseColor(listItemsTxt.get(position).Color))



         return view
    }

    override fun getItem(position: Int): Any? {

        return null

    }

    override fun getItemId(position: Int): Long {

        return 0

    }  

    override fun getCount(): Int {
        return listItemsTxt.size
    }

    private class ItemRowHolder(row: View?) {

        val label: TextView
        val imgDropDownMenuIcon:ImageView



        init {
            this.label = row?.findViewById(R.id.txtDropDownLabel) as TextView
            imgDropDownMenuIcon = row.findViewById(R.id.imgDropDownMenuIcon) as ImageView



         }
    }
}