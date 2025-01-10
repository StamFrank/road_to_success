package ru.success.road_to_success





import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    lateinit var tv: TextView
    lateinit var chb: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.textView)
        chb = findViewById(R.id.chbExtMenu)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // обновление меню
    override fun onPrepareOptionsMenu(menu: Menu): Boolean { // TODO Auto-generated method stub
        // пункты меню с ID группы = 1 видны, если в CheckBox стоит галка
        menu.setGroupVisible(1, chb.isChecked)
        return super.onPrepareOptionsMenu(menu)
    }

    // обработка нажатий
    override fun onOptionsItemSelected(item: MenuItem): Boolean { // TODO Auto-generated method stub
        val sb = StringBuilder()


        // Выведем в TextView информацию о нажатом пункте меню
        sb.append("Item Menu")
        sb.append(
            """
 groupId: ${item.groupId}"""
        )
        sb.append(
            """
 itemId: ${item.itemId}"""
        )
        sb.append(
            """
 order: ${item.order}"""
        )
        sb.append(
            """
 title: ${item.title}"""
        )
        tv.text = sb.toString()

        return super.onOptionsItemSelected(item)
    }

}