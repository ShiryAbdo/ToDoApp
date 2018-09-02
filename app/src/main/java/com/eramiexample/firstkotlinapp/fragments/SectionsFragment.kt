package com.eramiexample.firstkotlinapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.eramiexample.firstkotlinapp.R
import com.eramiexample.firstkotlinapp.adapters.TaskAdaptor
import com.eramiexample.firstkotlinapp.sql.DbManager
import com.eramiexample.firstkotlinapp.model.Tasks
import kotlinx.android.synthetic.main.content_bas.*


class SectionsFragment : Fragment() {
    private var viewReturn :View?=null
    private val arrayListTasks= ArrayList<Tasks>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewReturn = inflater.inflate(R.layout.fragment_parts, container, false)
        recyclerView.layoutManager= GridLayoutManager(activity,2)
        selectCatogory("Home")


        return viewReturn
    }
    private  fun selectCatogory(catogory:String){
        var dbManager= DbManager(this.activity!!)
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

        recyclerView.adapter= TaskAdaptor(this.activity!!, arrayListTasks) {
            //                     Toast.makeText(this@BasActivity,it.title,Toast.LENGTH_LONG).show()

        }

    }



}
