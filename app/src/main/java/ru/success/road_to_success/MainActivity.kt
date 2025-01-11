package ru.success.road_to_success

import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    lateinit var tvSize: TextView
    lateinit var tvColor: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvColor = findViewById(R.id.tvColor);
        tvSize = findViewById(R.id.tvSize);

        // для tvColor и tvSize необходимо создавать контекстное меню

        registerForContextMenu(tvColor);
        registerForContextMenu(tvSize);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        when (v!!.id) {
            R.id.tvColor -> {
                menuInflater.inflate(R.menu.mymenu, menu)
            }

            R.id.tvSize  -> {
                menuInflater.inflate(R.menu.mymenu2, menu)

            }
        }

        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.MENU_COLOR_RED   -> {
                tvColor.setTextColor(Color.RED)
                tvColor.text = "Text color = red"
            }

            R.id.MENU_COLOR_GREEN -> {
                tvColor.setTextColor(Color.GREEN)
                tvColor.text = "Text color = green"
            }

            R.id.MENU_COLOR_BLUE  -> {
                tvColor.setTextColor(Color.BLUE)
                tvColor.text = "Text color = blue"
            }

            R.id.MENU_SIZE_22     -> {
                tvSize.textSize = 22f
                tvSize.text = "Text size = 22"
            }

            R.id.MENU_SIZE_26     -> {
                tvSize.textSize = 26f
                tvSize.text = "Text size = 26"
            }

            R.id.MENU_SIZE_30     -> {
                tvSize.textSize = 30f
                tvSize.text = "Text size = 30"
            }
        }
        return super.onContextItemSelected(item)

    }



    }
