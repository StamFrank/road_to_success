package ru.success.road_to_success

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.example.MessagePhotosCMID
import com.example.example.PojoClass
import com.google.gson.GsonBuilder
import com.vk.api.sdk.utils.VKUtils
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.CountDownLatch


class MainActivity() : AppCompatActivity(), OnClickListener {
    private lateinit var tokenUpdate: Button
    private lateinit var btnLink: Button
    private lateinit var btnSavePhotoData: Button
    private lateinit var btnDel: Button
    private lateinit var btnUpd: Button
    private lateinit var friendID: EditText
    private lateinit var tokenID: EditText
    private lateinit var dataGson: TextView
    private lateinit var btnGson: Button
    var tokenGlobal: String = ""
    var cmidGlobal: String = ""
    var ownerIDGlobal: String = ""
    var friendIDGlobal: String = ""

    var cmidsPredator: Int = 100
    var cmid: String = ""
    var photosUrls = mutableListOf<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        initUI()


        val fingerprints: Array<String?>? = VKUtils.getCertificateFingerprint(
            this, this.packageName
        )
        if (fingerprints != null) {
            Log.d("sislok", fingerprints.contentToString())
        }

    }


    private fun initUI() {

        tokenUpdate = findViewById(R.id.tokenUpdate)
        tokenUpdate.setOnClickListener { handleOnTokenUpdateClicked() }


        btnLink = findViewById(R.id.btnLink)
        btnLink.setOnClickListener { handleOnBtnLinkClicked() }


        tokenID = findViewById(R.id.tokenID)
        friendID = findViewById(R.id.friendID)


        btnSavePhotoData = findViewById(R.id.btnSavePhotoData)
        btnSavePhotoData.setOnClickListener { handleOnBtnSavePhotoDataClicked() }
//
//        btnAdd = findViewById(R.id.btnAdd)
//        btnAdd.setOnClickListener { handleOnBtnAddClicked() }
//
//        btnRead = findViewById(R.id.btnRead)
//        btnRead.setOnClickListener { handleOnBtnReadClicked() }
//
//        btnClear = findViewById(R.id.btnClear)
//        btnClear.setOnClickListener { handleOnBtnClearClicked() }
//
//        etName = findViewById(R.id.etName)
//        etEmail = findViewById(R.id.etEmail)
    }


    private fun handleOnBtnSavePhotoDataClicked() {
        Log.d("sislok", "Прошло1")

        val client = OkHttpClient()

        while (cmidsPredator >= 3) {

            val latch = CountDownLatch(1)

            var url = "https://api.vk.com/method/messages.getHistoryAttachments?peer_id=$friendIDGlobal&cmid=$cmidGlobal&count=200&attachment_types=photo&access_token=$tokenGlobal&v=5.199"
            var urlUltra = url

            val request = Request.Builder().url(urlUltra).build()

            client.newCall(request).enqueue(object : okhttp3.Callback {

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    Log.d("sislok", body.toString())
                    val gson = GsonBuilder().create()
                    val rresponse = gson.fromJson(body, PojoClass::class.java)

                    val urlPattern = "url=(https?:\\/\\/[^\\s)]+)".toRegex()
                    val cmidPattern = "cmid=(\\d+)".toRegex()

                    var inputedString = rresponse.toString()

                    val urls = urlPattern.findAll(inputedString).map { it.groupValues[1] }.toList()
                    val cmids =
                        cmidPattern.findAll(inputedString).map { it.groupValues[1] }.toList()

                    println("Extracted URLs:")
                    urls.forEach { photosUrls.add(it); println(it) }
                    println("\nExtracted CMIDs:")
                    cmids.forEach { cmid = it; println(it) }
                    cmidsPredator = cmids.count()
                    cmidGlobal = cmids.lastOrNull() ?: ""

                    latch.countDown()
                }


                override fun onFailure(call: Call, e: IOException) {
                    Log.d("sislok", "Ошибка запроса")
                    latch.countDown()
                }
            })
            latch.await()
        }
        saveUrlsToTxt(photosUrls)

    }

    private fun handleOnBtnLinkClicked() {

        var firstUrl = tokenID.text.toString()
        friendIDGlobal = friendID.text.toString()
        val tokenRegexEX = """access_token=([^&]+)""".toRegex()
        val userIdRegexEX = """user_id=([^&]+)""".toRegex()

        val tokenMatch = tokenRegexEX.find(firstUrl)?.groupValues?.get(1)
        tokenGlobal = tokenMatch.toString()

        val userIdMatch = userIdRegexEX.find(firstUrl)?.groupValues?.get(1)
        ownerIDGlobal = userIdMatch.toString()
        var cmidBestUrl = "https://api.vk.com/method/messages.getHistory?peer_id=$friendIDGlobal&count=1&access_token=$tokenGlobal&v=5.199"


        val requester = Request.Builder().url(cmidBestUrl).build()

        val client = OkHttpClient()
        client.newCall(requester).enqueue(object: okhttp3.Callback{

            override fun onResponse(call: Call, response: Response) {
                val bodys = response.body?.string()
                val gson = GsonBuilder().create()
                val rresponse = gson.fromJson(bodys, MessagePhotosCMID::class.java)
                var ultraresponse = rresponse.toString()
                val regex = """conversationMessageId\s*=\s*(\d+)""".toRegex()
                val match = regex.find(ultraresponse)
                val conversationMessageId = match?.groupValues?.get(1)
                cmidGlobal = conversationMessageId.toString()
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d("sislok", "Ошибка запроса")
            }
        })
    }

    private fun handleOnBtnReadClicked() {

    }

    private fun handleOnBtnUpdateClicked() {

    }

    private fun handleOnTokenUpdateClicked() {
        val url = "https://oauth.vk.com/authorize?client_id=6121396&scope=photos,video,messages,offline&redirect_uri=https://oauth.vk.com/blank.html&response_type=token&display=page"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun handleOnBtnClearClicked() {

    }


    override fun onClick(p0: View?) {

    }


    fun saveUrlsToTxt(photosUrls: List<String>) {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val file = File(downloadsDir, "photos_urls.txt")

        try {
            FileOutputStream(file).use { fos ->
                val content = photosUrls.joinToString(separator = "\n")

                fos.write(content.toByteArray())
                fos.flush()

                println("Файл успешно сохранён в: ${file.absolutePath}")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            println("Ошибка при записи файла: ${e.message}")
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

