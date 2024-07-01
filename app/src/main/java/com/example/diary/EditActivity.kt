package com.example.diary

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diary.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private lateinit var dbHelper: DatabaseHelper
    private var textFeeling: String? = ""
    private var textAction: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.extras?.let {
            binding.diaryDate.setText(it.getString("DIARY_DATE"))
            binding.diaryText.setText(it.getString("DIARY_TEXT"))
        }

        dbHelper = DatabaseHelper(this@EditActivity)

        binding.btnSave.setOnClickListener{
            if(binding.diaryDate.text.isNullOrBlank() || binding.diaryText.text.isNullOrBlank()) {
                Toast.makeText(this, "未入力の項目があります。", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                }

            dbHelper.writableDatabase.use { db ->
                var values = ContentValues().apply {
                    put("diary_date", binding.diaryDate.text.toString())
                    put("diary_text", binding.diaryText.text.toString())
                }
//                db.insert("items", null, values)
                db.insertWithOnConflict("items", null, values, SQLiteDatabase.CONFLICT_REPLACE)
                startActivity(Intent(this@EditActivity, MainActivity::class.java))
            }
        }

        binding.btnDelete.setOnClickListener{
            if (binding.diaryDate.text.isNullOrBlank()){
                Toast.makeText(this, "日付が選択されていません。", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            dbHelper.writableDatabase.use { db ->
                val params = arrayOf(binding.diaryDate.text.toString())
                db.delete("items", "diary_date = ?", params)
                startActivity(Intent(this@EditActivity, MainActivity::class.java))

            }

        }

        binding.diaryDate.setOnClickListener{
            val datePicker = DatePickerFragment()
            datePicker.show(supportFragmentManager, "datePicker")
        }

        binding.feelGreat.setOnClickListener{ feelClicked(it) }
        binding.feelGood.setOnClickListener{ feelClicked(it) }
        binding.feelNormal.setOnClickListener{ feelClicked(it) }
        binding.feelBad.setOnClickListener{ feelClicked(it) }
        binding.feelAwful.setOnClickListener{ feelClicked(it) }

        binding.actionWork.setOnClickListener{ actionClicked(it) }
        binding.actionWorkout.setOnClickListener{ actionClicked(it) }
        binding.actionShopping.setOnClickListener{ actionClicked(it) }
        binding.actionMovie.setOnClickListener{ actionClicked(it) }
        binding.actionSleep.setOnClickListener{ actionClicked(it) }
    }

    private fun feelClicked(view: View){
        textFeeling = when (view.id){
            R.id.feelGreat -> "今日の気分は最高！！"
            R.id.feelGood -> "今日の気分は良い！"
            R.id.feelNormal -> "今日の気分は普通。"
            R.id.feelBad -> "今日の気分は微妙。"
            else -> "今日の気分は最悪、、、"
        }
        updateDiaryText()
    }

    private fun actionClicked(view: View){
        textAction = when (view.id){
            R.id.actionWork -> "一日中仕事をした。"
            R.id.actionWorkout -> "しっかり運動をした。"
            R.id.actionShopping -> "友達と買い物に行った。"
            R.id.actionMovie -> "久しぶりに映画を見た。"
            else -> "ずっと寝ていた。"
        }

        updateDiaryText()
    }

    private fun updateDiaryText(){
        binding.diaryText.setText(getString(R.string.diary_text, textFeeling, textAction))
    }
}