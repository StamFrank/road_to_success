package ru.success.road_to_success




import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.absoluteValue


class MainActivity : AppCompatActivity(), OnClickListener{

    lateinit var textOut: EditText
    lateinit var chPlus: Button
    lateinit var chMinus: Button
    lateinit var proverka: CheckBox
    lateinit var lastText: TextView
    lateinit var sPref: SharedPreferences
    lateinit var btnLoad: Button

    var SAVED_TEXT: String = "integer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        lastText = findViewById(R.id.lastText)

        btnLoad = findViewById(R.id.btnLoad)
        btnLoad.setOnClickListener(this)

        proverka = findViewById(R.id.proverka)
        proverka.setOnClickListener(this)

        textOut = findViewById(R.id.textOut)
        textOut.setOnClickListener(this)

        chMinus = findViewById(R.id.chMinus)
        chMinus.setOnClickListener(this)

        chPlus = findViewById(R.id.chPlus)
        chPlus.setOnClickListener (this)

        lastText.setText("Last saved Int: " + loadText(savedText = SAVED_TEXT).toString())
    }

    override fun onClick(v: View?) {

        when (v!!.id){
            R.id.chPlus -> {
                var text = textOut.text.toString().toInt()
                var text1 = text + 1
                var text2 = text1.toString()
                textOut.setText(text2)
//                if(proverka.isChecked){
//                    saveText()
//                }
            }
            R.id.chMinus -> {
                var text = textOut.text.toString().toInt()
                var text1 = text - 1
                var text2 = text1.toString()
                textOut.setText(text2)
            }
            R.id.proverka -> {
                if(proverka.isChecked){
                    saveText()
                }
            }
            R.id.btnLoad -> {
                textOut.setText(loadText(savedText = SAVED_TEXT).toString())
            }
        }

    }

    private fun loadText(savedText: String): String? {
        sPref = getPreferences(MODE_PRIVATE)
        val savedText = sPref.getString(SAVED_TEXT, "")
        return savedText
    }

    private fun saveText() {
        sPref = getPreferences(MODE_PRIVATE)
        val ed = sPref.edit()
        ed.putString(SAVED_TEXT, textOut.text.toString())
        ed.apply()
    }

        override fun onStop() {

            if(proverka.isChecked){
                saveText()
            }
            super.onStop()
        }

}