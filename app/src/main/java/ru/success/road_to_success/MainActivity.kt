package ru.success.road_to_success





import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    val MENU_COLOR_RED: Int = 1
    val MENU_COLOR_GREEN: Int = 2
    val MENU_COLOR_BLUE: Int = 3

    val MENU_SIZE_22: Int = 4
    val MENU_SIZE_26: Int = 5
    val MENU_SIZE_30: Int = 6

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
        menu: ContextMenu,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        when (v!!.id) {
            R.id.tvColor -> {
                menu.add(0, MENU_COLOR_RED, 0, "Red")
                menu.add(0, MENU_COLOR_GREEN, 0, "Green")
                menu.add(0, MENU_COLOR_BLUE, 0, "Blue")
            }

            R.id.tvSize  -> {
                menu.add(0, MENU_SIZE_22, 0, "22")
                menu.add(0, MENU_SIZE_26, 0, "26")
                menu.add(0, MENU_SIZE_30, 0, "30")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            MENU_COLOR_RED   -> {
                tvColor.setTextColor(Color.RED)
                tvColor.text = "Text color = red"
            }

            MENU_COLOR_GREEN -> {
                tvColor.setTextColor(Color.GREEN)
                tvColor.text = "Text color = green"
            }

            MENU_COLOR_BLUE  -> {
                tvColor.setTextColor(Color.BLUE)
                tvColor.text = "Text color = blue"
            }

            MENU_SIZE_22     -> {
                tvSize.textSize = 22f
                tvSize.text = "Text size = 22"
            }

            MENU_SIZE_26     -> {
                tvSize.textSize = 26f
                tvSize.text = "Text size = 26"
            }

            MENU_SIZE_30     -> {
                tvSize.textSize = 30f
                tvSize.text = "Text size = 30"
            }
        }
        return super.onContextItemSelected(item)

    }


    }
