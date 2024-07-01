package com.example.diary

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return activity?.let {
            DatePickerDialog(
                it,
                this,
                year,
                month,
                day
            )
        } ?: throw IllegalStateException("アクティビティがNullです。")
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        activity?.findViewById<EditText>(R.id.diaryDate)?.setText(
            getString(R.string.formatted_date, year, month+1, dayOfMonth)
        )
    }
}