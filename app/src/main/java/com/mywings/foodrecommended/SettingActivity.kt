package com.mywings.foodrecommended

import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import kotlinx.android.synthetic.main.activity_setting.*
import java.text.SimpleDateFormat
import java.util.*

class SettingActivity : AppCompatActivity() {

    private val calendar = Calendar.getInstance()!!
    private lateinit var time: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)

        lblGymTimeMor.setOnClickListener {
            TimePickerDialog(
                this@SettingActivity,
                timeSetGymListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this@SettingActivity)
            ).show()


        }

        lblTimeForBreakfast.setOnClickListener {

            TimePickerDialog(
                this@SettingActivity,
                timeSetBreakfastListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this@SettingActivity)
            ).show()

        }

        lblTimeForLunch.setOnClickListener {

            TimePickerDialog(
                this@SettingActivity,
                timeSetLunchListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this@SettingActivity)
            ).show()

        }

        lblTimeForDinner.setOnClickListener {
            TimePickerDialog(
                this@SettingActivity,
                timeSetDinnerListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this@SettingActivity)
            ).show()

        }

        btnSave.setOnClickListener {

            for(i in 0..4){

            }

        }

    }


    private val timeSetGymListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        lblGymTimeMor.text = convertToTime(calendar)
    }

    private val timeSetBreakfastListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        lblTimeForBreakfast.text = convertToTime(calendar)
    }


    private val timeSetLunchListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        lblTimeForLunch.text = convertToTime(calendar)
    }
    private val timeSetDinnerListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        lblTimeForDinner.text = convertToTime(calendar)
    }


    private fun convertToTime(calendar: Calendar) = SimpleDateFormat("HH:mm").format(calendar.time)


}
