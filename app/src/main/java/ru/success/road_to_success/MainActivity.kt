package ru.success.road_to_success




import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    var tvOut: TextView? = null
    var btnOk: Button? = null
    var btnCancel: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvOut = findViewById(R.id.tvOut)
        btnOk = findViewById(R.id.btnOk)
        btnCancel = findViewById(R.id.btnCancel)

        val oclBtnOk = View.OnClickListener {

            tvOut?.setText("Нажата кнопка ОК");
        }

        btnOk?.setOnClickListener(oclBtnOk);

        val oclBtnCancel = object : View.OnClickListener {
            override fun onClick(v: View?) { // Меняем текст в TextView (tvOut)
                tvOut!!.text = "Нажата кнопка Cancel"
            }
        }

        btnCancel?.setOnClickListener(oclBtnCancel);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}