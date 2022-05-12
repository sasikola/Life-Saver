package com.example.tryfib1

import androidx.appcompat.app.AppCompatActivity
import android.widget.CalendarView
import android.widget.TextView
import android.os.Bundle
import android.view.View
import com.example.tryfib1.R
import android.widget.CalendarView.OnDateChangeListener

/***************************************************************
 *
 * Unwanted class
 *
 * class F
 * to get date from CalenderView
 * in DD/MM/YYYY format
 * converted java code to kotlin and used in main activity
 */

class F : AppCompatActivity() {
    var calendarView: CalendarView? = null
    var myBate: TextView? = null
    var myDate: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calendarView = findViewById<View>(R.id.calendarView) as CalendarView
        calendarView!!.setOnDateChangeListener { calendarView, i, i1, i2 ->
            myDate = i2.toString() + "/" + (i1 + 1) + "/" + i
        }
    }
}

