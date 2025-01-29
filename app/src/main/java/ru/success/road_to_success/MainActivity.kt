package ru.success.road_to_success




import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.time.Duration.Companion.milliseconds


class MainActivity : AppCompatActivity(), OnClickListener{

    lateinit var textOut: EditText
    lateinit var chPlus: Button
    lateinit var chMinus: Button
    lateinit var proverka: CheckBox
    lateinit var sPref: SharedPreferences
    var provCh: Boolean = false

    var SAVED_TEXT: String = "string_new"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        proverka = findViewById(R.id.proverka)
        proverka.setOnClickListener(this)

        textOut = findViewById(R.id.textOut)

        chMinus = findViewById(R.id.chMinus)
        chMinus.setOnClickListener(this)

        chPlus = findViewById(R.id.chPlus)
        chPlus.setOnClickListener (this)

        loadText()


    }
    override fun onClick(v: View?) {

        when (v!!.id){
            R.id.chPlus -> {
                var text = textOut.text.toString().toInt()
                var text1 = text + 1
                var text2 = text1.toString()
                textOut.setText(text2)
            }
            R.id.chMinus -> {
                var text = textOut.text.toString().toInt()
                var text1 = text - 1
                var text2 = text1.toString()
                textOut.setText(text2)
            }
            R.id.proverka ->{
                provCh = proverka.isChecked
            }
        }

    }

    private fun loadText() {
        sPref = getPreferences(MODE_PRIVATE)
        var savedText = sPref.getString(SAVED_TEXT, "")
        textOut.setText(savedText)
        Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show()
    }

    private fun saveText() {
        sPref = getPreferences(MODE_PRIVATE)
        var ed = sPref.edit()
        ed.putString(SAVED_TEXT, textOut.text.toString())
        ed.apply()
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        when (provCh) {
            true  -> {
                saveText()
            }

            false -> {
                loadText()
            }
        }

    }
}