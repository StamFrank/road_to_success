package ru.success.road_to_success




import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), OnClickListener {

    lateinit var btnActTwo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        btnActTwo = findViewById(R.id.btnActTwo)
        btnActTwo.setOnClickListener(this)


    }

    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.btnActTwo -> {
            }
        }

    }
}