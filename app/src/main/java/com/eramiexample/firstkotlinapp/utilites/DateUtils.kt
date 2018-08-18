package com.eramiexample.firstkotlinapp.utilites

import java.text.SimpleDateFormat
import java.util.*


object DateUtils {
        @JvmStatic
        fun toSimpleString(date: Date): String {
            val format = SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")
            return format.format(date)
        }
}
