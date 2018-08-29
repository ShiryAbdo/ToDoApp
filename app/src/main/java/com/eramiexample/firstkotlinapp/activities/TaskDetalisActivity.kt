package com.eramiexample.firstkotlinapp.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.eramiexample.firstkotlinapp.R
import com.eramiexample.firstkotlinapp.utilites.Tasks

import kotlinx.android.synthetic.main.activity_task_detalis2.*

class TaskDetalisActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detalis2)
        setSupportActionBar(toolbar)
        var task = getIntent().getExtras().get("task") as? Tasks
        Toast.makeText(this@TaskDetalisActivity, ""+task!!.id,Toast.LENGTH_LONG).show()

        delet.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}