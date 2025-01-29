package ru.success.road_to_success




import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.absoluteValue


class MainActivity : AppCompatActivity(), OnClickListener{

    lateinit var textOut: EditText
    lateinit var chPlus: Button
    lateinit var chMinus: Button
    lateinit var proverka: CheckBox
    lateinit var sPref: SharedPreferences

    var SAVED_TEXT: String = "integer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        proverka = findViewById(R.id.proverka)
        proverka.setOnClickListener(this)

        textOut = findViewById(R.id.textOut)
        textOut.setOnClickListener(this)

        chMinus = findViewById(R.id.chMinus)
        chMinus.setOnClickListener(this)

        chPlus = findViewById(R.id.chPlus)
        chPlus.setOnClickListener (this)

//        loadText()


    }

    override fun onClick(v: View?) {

        when (v!!.id){
            R.id.chPlus -> {
                var text = textOut.text.toString().toInt()
                var text1 = text + 1
                var text2 = text1.toString()
                textOut.setText(text2)
                saveText()
            }
            R.id.chMinus -> {
                var text = textOut.text.toString().toInt()
                var text1 = text - 1
                var text2 = text1.toString()
                textOut.setText(text2)
                saveText()
            }
            R.id.proverka ->{
                if(proverka.isChecked){
                    saveText()
                }
            }
        }

    }

    private fun loadText() {
        sPref = getPreferences(MODE_PRIVATE)
        var savedText = sPref.getInt(SAVED_TEXT, 0)
        textOut.setText(savedText.absoluteValue)
    }

    private fun saveText() {
        sPref = getPreferences(MODE_PRIVATE)
        var ed = sPref.edit()
        ed.putInt(SAVED_TEXT, textOut.text.toString().toInt())
        ed.apply()
//        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show()
    }
}