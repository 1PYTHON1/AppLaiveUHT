package com.laivedatos.uht_v2

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment (val listener: (minute:Int,hour:Int) -> Unit): DialogFragment(),
        TimePickerDialog.OnTimeSetListener{

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        listener(minute,hourOfDay)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val minute = c.get(Calendar.MINUTE)
        val hour= c.get(Calendar.HOUR_OF_DAY)
        val picker = TimePickerDialog(activity as Context,this ,minute,hour,true)
        return picker
    }
    


}
