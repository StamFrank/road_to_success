package ru.success.road_to_success

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), OnClickListener {

    val MENU_RESET_ID: Int = 1
    val MENU_QUIT_ID: Int = 2

    lateinit var etNum1: EditText
    lateinit var etNum2: EditText

    lateinit var btnAdd: Button
    lateinit var btnSub: Button
    lateinit var btnMult: Button
    lateinit var btnDiv: Button

    lateinit var tvResult: TextView

    var oper: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etNum1 = findViewById(R.id.etNum1)
        etNum2 = findViewById(R.id.etNum2)

        btnAdd = findViewById(R.id.btnAdd)
        btnSub = findViewById(R.id.btnSub)
        btnMult = findViewById(R.id.btnMult)
        btnDiv = findViewById(R.id.btnDiv)

        tvResult = findViewById(R.id.tvResult)

        btnAdd.setOnClickListener(this)
        btnSub.setOnClickListener(this)
        btnMult.setOnClickListener(this)
        btnDiv.setOnClickListener(this)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menu!!.add(0, MENU_RESET_ID, 0, "Reset");
        menu.add(0, MENU_QUIT_ID, 0, "Quit");
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            MENU_RESET_ID -> {
                etNum1.setText("")
                etNum2.setText("")
            }
            MENU_QUIT_ID -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        var num1 = 0f
        var num11: String? = null
        var num2 = 0f
        var num22: String? = null
        var result = 0f
        var nChk = "0"

        if (TextUtils.isEmpty(etNum1.text.toString())
            || TextUtils.isEmpty(etNum2.text.toString())) {
            return
        }
        num11 = etNum1.text.toString()
        num1 = num11.toFloat()
        num22 = etNum2.text.toString()
        num2 = num22.toFloat()

        when (v!!.id) {
            R.id.btnAdd  -> {
                oper = "+"
                result = num1 + num2
            }

            R.id.btnSub  -> {
                oper = "-"
                result = num1 - num2
            }

            R.id.btnMult -> {
                oper = "*"
                result = num1 * num2
            }

            R.id.btnDiv  -> {

                oper = "/"

                result = num1 / num2

            }

            else         -> {}

        }

        if (num22 == nChk){
            tvResult.text = "Произведено деление на ноль"
        }
        else {
            tvResult.text = "$num1 $oper $num2 = $result"
        }

    }
}