package com.eramiexample.firstkotlinapp.fragments


import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.eramiexample.firstkotlinapp.R
import com.eramiexample.firstkotlinapp.adaptors.MainPointsAdaptor
import com.eramiexample.firstkotlinapp.utilites.DbManager
import com.eramiexample.firstkotlinapp.utilites.TagsData


class PartsFragment : Fragment() {
      private val arrayListTasks= ArrayList<TagsData>()
    var recyclerViewp: RecyclerView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       val  viewReturn = inflater.inflate(R.layout.fragment_parts, container, false)
        recyclerViewp = viewReturn.findViewById(R.id.recyclerViewp) as RecyclerView // Add this
        recyclerViewp!!.layoutManager= GridLayoutManager(activity,2)
        LoadQuery("%")


        return viewReturn
    }

    private fun LoadQuery(title:String){

        val dbManager= DbManager(activity)
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
//                val getTask= Tasks(ID, Title, Description, TAG,imageView, date)
                var name:String =""

                    val getTask= TagsData(TAG,imageView)
                if(!arrayListTasks.contains(getTask)){
                    arrayListTasks.add(getTask)

                }



            }while (cursor.moveToNext())
        }
        recyclerViewp!!.adapter= MainPointsAdaptor(activity,arrayListTasks){
            //Toast.makeText(this@BasActivity,it.title,Toast.LENGTH_LONG).show()

        }





    }


}
