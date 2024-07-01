package com.example.diary.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.R

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val itemDate: TextView = itemView.findViewById(R.id.itemDate)
    val itemText: TextView = itemView.findViewById(R.id.itemText)
}