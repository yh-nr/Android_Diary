package com.example.diary

import android.content.Intent
import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diary.databinding.ActivityMainBinding
import com.example.diary.recyclerview.MyListAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val sampleData = mutableListOf(
//            mapOf ("date" to "2024/01/01", "text" to "ここに日記のテキストが入ります。"),
//            mapOf ("date" to "2024/02/01", "text" to "ここに日記のテキストが入ります。"),
//            mapOf ("date" to "2024/03/01", "text" to "ここに日記のテキストが入ります。"),
//            mapOf ("date" to "2024/04/01", "text" to "ここに日記のテキストが入ります。"),
//        )

//        binding.listView.adapter = SimpleAdapter(
//            this,
//            sampleData,
//            R.layout.list_item,
//            arrayOf("date", "text"),
//            intArrayOf(R.id.itemDate, R.id.itemText)
//        )

        val data = mutableListOf<Map<String, String>>()

        dbHelper = DatabaseHelper(this@MainActivity)
        dbHelper.readableDatabase.use { db ->
            val cursor = db.query(
                "items", null, null, null, null, null,
                "diary_date DESC", null
            )

            cursor.use{
                while (it.moveToNext()){
                    data.add(mapOf("date" to it.getString(0), "text" to it.getString(1)))
                }
            }
        }


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = MyListAdapter(data)

        val dividerItemDecoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)


        binding.fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, EditActivity::class.java))
        }
    }
}