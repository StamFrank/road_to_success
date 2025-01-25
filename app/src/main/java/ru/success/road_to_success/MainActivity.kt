package ru.success.road_to_success




import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity(), OnClickListener{

    lateinit var etText: EditText
    lateinit var btnSave: Button
    lateinit var btnLoad: Button
    lateinit var spinner: Spinner
    lateinit var sPref: SharedPreferences

    val SAVED_TEXT: String = "saved_text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        spinner = findViewById(R.id.spinner)

        etText = findViewById(R.id.etText)

        btnSave = findViewById(R.id.btnSave)
        btnSave.setOnClickListener(this)

        btnLoad = findViewById(R.id.btnLoad)
        btnLoad.setOnClickListener(this)





    }
    override fun onClick(v: View?) {

        when (v!!.id){
            R.id.btnSave -> {
                saveText()
            //    Toast.makeText(this, spinner.selectedItemId.toString(), Toast.LENGTH_SHORT).show()

            }
            R.id.btnLoad -> {
                loadText()
            }
        }

    }

    private fun loadText() {
        sPref = getPreferences(MODE_PRIVATE)
        val savedText = sPref.getString(SAVED_TEXT, "")
        etText.setText(savedText)
        Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show()
    }

    private fun saveText() {
        sPref = getPreferences(MODE_PRIVATE)
        val ed = sPref.edit()
        ed.putString(SAVED_TEXT, etText.text.toString())
        ed.apply()
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        when (spinner.selectedItemId.toString())
        {
            "1" -> {
                saveText()
            }
        }

    }
}