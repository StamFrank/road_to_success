package ru.success.road_to_success

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), OnSeekBarChangeListener {

    lateinit var sbWeight: SeekBar
    lateinit var btn1: Button
    lateinit var btn2: Button

    lateinit var lParams1: LinearLayout.LayoutParams
    lateinit var lParams2: LinearLayout.LayoutParams


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        sbWeight = findViewById(R.id.sbWeight)
        sbWeight.setOnSeekBarChangeListener(this)

        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)

        lParams1 = btn1.layoutParams as LinearLayout.LayoutParams
        lParams2 = btn2.layoutParams as LinearLayout.LayoutParams

    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        val leftValue: Int = sbWeight.progress
        val rightValue: Int = sbWeight.max - sbWeight.progress

        // настраиваем вес
        lParams1.weight = leftValue.toFloat()
        lParams2.weight = rightValue.toFloat()

        // в текст кнопок пишем значения переменных
        btn1.text = "$leftValue"
        btn2.text = "$rightValue"

    }

    override fun onStartTrackingTouch(p0: SeekBar?) {


    }

    override fun onStopTrackingTouch(p0: SeekBar?) {


    }
}