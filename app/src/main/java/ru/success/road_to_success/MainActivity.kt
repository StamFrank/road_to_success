package ru.success.road_to_success




import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        var linLayout = LinearLayout(this)
        linLayout.orientation = LinearLayout.VERTICAL
        var linLayoutParam = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        var IpView = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        var tv = TextView(this)
        tv.text = "TetView"
        tv.layoutParams = IpView
        linLayout.addView(tv)

        var btn = Button(this)
        btn.text = "Button"
        linLayout.addView(btn, IpView)

        setContentView(linLayout, linLayoutParam)

        val leftMarginParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
        )
        leftMarginParams.leftMargin = 50

        val btn1 = Button(this)
        btn1.text = "Button1"
        linLayout.addView(btn1, leftMarginParams)

        val rightGravityParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
        )
        rightGravityParams.gravity = Gravity.RIGHT

        val btn2 = Button(this)
        btn2.text = "Button2"
        linLayout.addView(btn2, rightGravityParams)




//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}