package com.eramiexample.firstkotlinapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tasks(val id:Int ,
                 val title:String ,
                 var descriptionTask:String,
                 var tag:String,
                 var imageView:String,
                 val createdA:String) : Parcelable