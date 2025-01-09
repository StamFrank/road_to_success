package ru.success.road_to_success

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val llBottom = findViewById<View>(R.id.llBottom) as LinearLayout
        val tvBottom = findViewById<View>(R.id.tvBottom) as TextView
        val btnBottom = findViewById<View>(R.id.btnBottom) as Button

        llBottom.setBackgroundResource(R.color.llBottomColor)
        tvBottom.setText(R.string.tvBottomText)
        btnBottom.setText(R.string.btnBottomText)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}