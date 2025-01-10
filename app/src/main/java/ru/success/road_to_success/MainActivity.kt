package ru.success.road_to_success

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    lateinit var tvOut: TextView
    lateinit var btnOk: Button
    lateinit var btnCancel: Button

    val TAG: String = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        Log.d(TAG, "найдем View-элементы");
        tvOut = findViewById(R.id.tvOut)
        btnOk = findViewById(R.id.btnOk)
        btnCancel = findViewById(R.id.btnCancel)
        Log.d(TAG, "присваиваем обработчик кнопкам");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun onClickStart(v: View?) {
        Log.d(TAG, "по id определяем кнопку, вызвавшую этот обработчик");
        if (v != null) {
            when (v.id) {

                R.id.btnOk     -> {      // кнопка ОК
                    tvOut.text = "Нажата кнопка ОК"
                    Log.d(TAG, "кнопка ОК")
                    Toast.makeText(this, "Нажата кнопка ОК", Toast.LENGTH_LONG).show()
                }

                R.id.btnCancel -> {       // кнопка Cancel
                    tvOut.text = "Нажата кнопка Cancel"
                    Log.d(TAG, "кнопка Cancel")
                    Toast.makeText(this, "Нажата кнопка Cancel", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}