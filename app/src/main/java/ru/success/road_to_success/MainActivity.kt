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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var btnAdd: Button
    private lateinit var btnRead: Button
    private lateinit var btnClear: Button
    private lateinit var btnDel: Button
    private lateinit var btnUpd: Button
    private lateinit var etID: EditText
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initUI()
        initDB()
    }


    private fun initUI() {
        etID = findViewById(R.id.etID)

        btnDel = findViewById(R.id.btnDel)
        btnDel.setOnClickListener { handleOnBtnDeleteClicked() }

        btnUpd = findViewById(R.id.btnUpd)
        btnUpd.setOnClickListener { handleOnBtnUpdateClicked() }

        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener { handleOnBtnAddClicked() }

        btnRead = findViewById(R.id.btnRead)
        btnRead.setOnClickListener { handleOnBtnReadClicked() }

        btnClear = findViewById(R.id.btnClear)
        btnClear.setOnClickListener { handleOnBtnClearClicked() }

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
    }

    private fun initDB() {
        dbHelper = DBHelper(this)
    }


    private fun writeToDB(
        operation: (ContentValues, SQLiteDatabase) -> Unit
    ) {
        try {
            operation(
                ContentValues(),
                dbHelper.writableDatabase
            )
        } catch (e: Exception) {
            Log.d(LOG_TAG, "unable to write to DB, message: ${e.message}")
        } finally {
            dbHelper.close()
        }
    }

    private fun readFromDB(
        operation: (SQLiteDatabase) -> Unit
    ) {
        try {
            operation(
                dbHelper.writableDatabase
            )
        } catch (e: Exception) {
            Log.d(LOG_TAG, "unable to read from DB, message: ${e.message}")
        } finally {
            dbHelper.close()
        }
    }


    private fun handleOnBtnAddClicked() {
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        if(name.isNotEmpty() && email.isNotEmpty()) {
            writeToDB { contentValues, dataBase ->
                contentValues.put(DB_FIELD_NAME, name)
                contentValues.put(DB_FIELD_EMAIL, email)

                dataBase.insert(TABLE_NAME, null, contentValues)

                etName.text = null
                etEmail.text = null
            }
        } else {
            Log.d(LOG_TAG, "input fields are filled in incorrectly")
        }
    }

    private fun handleOnBtnReadClicked() {
        readFromDB { dataBase ->
            var navArgs = "List of rows: \n"
            val cursor = dataBase.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )

            val idColIndex = cursor.getColumnIndex(DB_FIELD_ID)
            val nameColIndex = cursor.getColumnIndex(DB_FIELD_NAME)
            val emailColIndex = cursor.getColumnIndex(DB_FIELD_EMAIL)

            while (cursor.moveToNext()) {
                val id = cursor.getInt(idColIndex)
                val name = cursor.getString(nameColIndex)
                val email = cursor.getString(emailColIndex)

                val currentRow = "$DB_FIELD_ID = $id, $DB_FIELD_NAME = $name, $DB_FIELD_EMAIL = $email"

                Log.d(LOG_TAG, currentRow)

                navArgs += "\n $currentRow"
            }
            cursor.close()

            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra(NAVIGATION_INTENT, navArgs)
            startActivity(intent)
        }
    }

    private fun handleOnBtnUpdateClicked() {
        writeToDB { contentValues, dataBase ->
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val id = etID.text.toString()

            if (name.isEmpty() || email.isEmpty() || id.isEmpty()) {
                Log.d(LOG_TAG, "input fields are filled in incorrectly")
            } else {
                contentValues.put(DB_FIELD_NAME, name)
                contentValues.put(DB_FIELD_EMAIL, email)
                val updCount = dataBase.update(
                    TABLE_NAME,
                    contentValues,
                    "$DB_FIELD_ID = ?",
                    arrayOf(id)
                )
                Log.d(LOG_TAG, "updated rows count = $updCount")
            }
        }
    }

    private fun handleOnBtnDeleteClicked() {
        writeToDB { _, dataBase ->
            val id = etID.text.toString()
            val delCount = dataBase.delete(
                TABLE_NAME,
                "$DB_FIELD_ID = $id",
                null
            )
            Log.d(LOG_TAG, "deleted rows count = $delCount")
        }
    }

    private fun handleOnBtnClearClicked() {
        writeToDB { _, dataBase ->
            val delCount = dataBase.delete(
                TABLE_NAME,
                null,
                null
            )
            Log.d(LOG_TAG, "deleted rows count = $delCount")
        }
    }


    override fun onClick(p0: View?) {

    }

    class DBHelper(context: Context?) : SQLiteOpenHelper(
        context,
        DB_NAME,
        null,
        DB_VERSION
    ) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(DB_CREATE_REQUEST)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        }
    }

    companion object {
        private const val LOG_TAG: String = "myLogs"
        private const val TABLE_NAME: String = "mytable"
        private const val NAVIGATION_INTENT: String = "SendListik"

        private const val DB_NAME: String = "myDB"
        private const val DB_VERSION: Int = 1
        private const val DB_FIELD_ID: String = "id"
        private const val DB_FIELD_NAME: String = "name"
        private const val DB_FIELD_EMAIL: String = "email"

        private const val DB_CREATE_REQUEST: String =
            "create table $TABLE_NAME " +
                    "(" +
                    "$DB_FIELD_ID integer primary key autoincrement," +
                    "$DB_FIELD_NAME text," +
                    "$DB_FIELD_EMAIL text" +
                    ");"
    }
}