package com.example.a7minuteworkoutapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        setSupportActionBar(toolbar_finish_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        btnFinish.setOnClickListener {
            finish()
        }
            addDateToDatabase()
    }
    private fun addDateToDatabase(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        val date = sdf.format(dateTime)

        val dbHandler = SqliteOpenHelper(this,null)
        dbHandler.addDate(date)
    }
}