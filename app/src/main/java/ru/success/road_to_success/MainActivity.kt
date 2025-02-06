package ru.success.road_to_success
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly


class MainActivity : AppCompatActivity(), OnClickListener {

    val LOG_TAG: String = "myLogs"
    lateinit var btnAdd: Button
    lateinit var btnRead: Button
    lateinit var btnClear: Button
    lateinit var btnDel: Button
    lateinit var btnUpd: Button
    lateinit var etID: EditText
    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var listAll: TextView

    var dbHelper: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etID = findViewById(R.id.etID)
        etID.setOnClickListener(this)

        btnDel = findViewById(R.id.btnDel)
        btnDel.setOnClickListener(this)

        btnUpd = findViewById(R.id.btnUpd)
        btnUpd.setOnClickListener(this)

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

//        listAll = findViewById(R.id.listAll)

        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etName = findViewById(R.id.etName);
        etName.setSelectAllOnFocus(true)
        etEmail = findViewById(R.id.etEmail);
        etEmail.setSelectAllOnFocus(true)

        // создаем объект для создания и управления версиями БД
        dbHelper = DBHelper(this);

    }


    override fun onClick(v: View?) {


        // создаем объект для данных
        val cv = ContentValues()


        // получаем данные из полей ввода
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val id = etID.text.toString()


        // подключаемся к БД
        val db = dbHelper!!.writableDatabase


        when (v!!.id) {
            R.id.btnAdd   -> {
                Log.d(LOG_TAG, "--- Insert in mytable: ---")

                // подготовим данные для вставки в виде пар: наименование столбца - значение
                cv.put("name", name)
                cv.put("email", email) // вставляем запись и получаем ее ID
                val rowID = db.insert("mytable", null, cv)
                Log.d(LOG_TAG, "row inserted, ID = $rowID")
                etName.text=null
                etEmail.text=null
            }

            R.id.btnRead  -> {
                var listik = ""
                Log.d(
                    LOG_TAG, "--- Rows in mytable: ---"
                ) // делаем запрос всех данных из таблицы mytable, получаем Cursor
                val c = db.query("mytable", null, null, null, null, null, null)


                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) { // определяем номера столбцов по имени в выборке
                    listik = "List of rows: \n"
                    val idColIndex = c.getColumnIndex("id")
                    val nameColIndex = c.getColumnIndex("name")
                    val emailColIndex = c.getColumnIndex("email")

                    do { // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(
                            LOG_TAG,
                            "ID = " + c.getInt(idColIndex) + ", name = " + c.getString(nameColIndex) + ", email = " + c.getString(
                                emailColIndex
                            )
                        ) // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                        listik += ("\nID = " + c.getInt(idColIndex) + ", name = " + c.getString(nameColIndex) + ", email = " + c.getString(
                            emailColIndex))
                    } while (c.moveToNext())
                } else Log.d(LOG_TAG, "0 rows")
                c.close()
                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("SendListik", listik)
                startActivity(intent)

            }

            R.id.btnUpd -> {

                if((name != "") && (email != "")) {

                    Log.d(LOG_TAG, "--- Update mytable: ---")
                    cv.put("name", name)
                    cv.put("email", email)
                    val updCount = db.update("mytable", cv, "id = ?", arrayOf(id))
                    Log.d(LOG_TAG, "updated rows count = $updCount")
                }


            }

            R.id.btnDel -> {

                if ((id != "") && (id.isDigitsOnly())) {

                    Log.d(LOG_TAG, "--- Delete from mytable: ---");
                    val delCount = db.delete("mytable", "id = $id", null)
                    Log.d(LOG_TAG, "deleted rows count = $delCount")
                }

            }

            R.id.btnClear -> {
                Log.d(LOG_TAG, "--- Clear mytable: ---") // удаляем все записи
                val clearCount = db.delete("mytable", null, null)
                Log.d(LOG_TAG, "deleted rows count = $clearCount")
            }
            R.id.etID -> {
                etID.setText("")
            }
        }

        // закрываем подключение к БД
        dbHelper!!.close()


    }


    class DBHelper(context: Context?) : SQLiteOpenHelper(context, "myDB", null, 1) {
        override fun onCreate(db: SQLiteDatabase) {
//            Log.d(LOG_TAG, "--- onCreate database ---") // создаем таблицу с полями
            db.execSQL(
                ("create table mytable (" + "id integer primary key autoincrement," + "name text," + "email text" + ");")
            )
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        }
    }


}