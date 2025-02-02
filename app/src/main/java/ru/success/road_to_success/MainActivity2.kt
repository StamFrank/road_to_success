package ru.success.road_to_success

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity2 : AppCompatActivity(), OnClickListener {
    lateinit var backMenu: Button
    lateinit var listAll: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        listAll = findViewById(R.id.listAll)

        backMenu = findViewById(R.id.backMenu)
        backMenu.setOnClickListener(this)
        var listik = intent.getStringExtra("SendListik")
        listAll.text = listik
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.backMenu ->
            {
                finish()
            }
        }
    }
}