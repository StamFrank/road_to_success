package ru.success.road_to_success

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.example.AlbumIDRequest
import com.example.example.AlbumPhotos
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
    private lateinit var friendID: EditText
    private lateinit var tokenID: EditText
    private lateinit var btnSaveAlbum: Button


    var tokenGlobal: String = ""
    var cmidGlobal: String = ""
    var ownerIDGlobal: String = ""
    var friendIDGlobal: String = ""
    var idAlbumSelected: String = "saved"
    val defaultAlbums = arrayListOf("saved", "wall", "profile")
    lateinit var idAlbumItems: Spinner
    lateinit var spinnerAdapter: ArrayAdapter<String>


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

        idAlbumItems = findViewById(R.id.idAlbumItems)
        spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, defaultAlbums)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        idAlbumItems.adapter = spinnerAdapter
        idAlbumItems.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                idAlbumSelected = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        tokenUpdate = findViewById(R.id.tokenUpdate)
        tokenUpdate.setOnClickListener { handleOnTokenUpdateClicked() }


        btnLink = findViewById(R.id.btnLink)
        btnLink.setOnClickListener { handleOnBtnLinkClicked() }


        tokenID = findViewById(R.id.tokenID)
        friendID = findViewById(R.id.friendID)


        btnSavePhotoData = findViewById(R.id.btnSavePhotoData)
        btnSavePhotoData.setOnClickListener { handleOnBtnSavePhotoDataClicked() }



        btnSaveAlbum = findViewById(R.id.btnSaveAlbum)
        btnSaveAlbum.setOnClickListener { handleOnBtnSaveAlbumClicked() }
//
//        etName = findViewById(R.id.etName)
//        etEmail = findViewById(R.id.etEmail)
    }


    private fun handleOnBtnSavePhotoDataClicked() {
        Log.d("sislok", "Прошло1")
        photosUrls.clear()

        val clientis = OkHttpClient()

        while (cmidsPredator >= 3) {

            val latch = CountDownLatch(1)

            var url = "https://api.vk.com/method/messages.getHistoryAttachments?peer_id=$friendIDGlobal&cmid=$cmidGlobal&count=200&attachment_types=photo&access_token=$tokenGlobal&v=5.199"
            var urlUltra = url

            val request = Request.Builder().url(urlUltra).build()

            clientis.newCall(request).enqueue(object : okhttp3.Callback {

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

        var albumIdBestUrl = "https://api.vk.com/method/photos.getAlbums?owner_id=$ownerIDGlobal&access_token=$tokenGlobal&v=5.199"

        val requesterer = Request.Builder().url(albumIdBestUrl).build()

        val clienter = OkHttpClient()
        clienter.newCall(requesterer).enqueue(object: okhttp3.Callback{

            override fun onResponse(call: Call, response: Response) {
                val boders = response.body?.string()
                val gson = GsonBuilder().create()
                val responses = gson.fromJson(boders, AlbumIDRequest::class.java)
                var megaresponse = responses.toString()
                val regex = """id=(\d+)""".toRegex()
                val matchId = regex.findAll(megaresponse)
                val newItems = mutableListOf<String>()
                for (match in matchId) {
                    val itemId = match.groupValues[1]
                    if (!defaultAlbums.contains(itemId)) {
                        newItems.add(itemId)
                    }
                }

                runOnUiThread { addItemsToSpinner(newItems) }


            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d("sislok", "Ошибка запроса")
            }
        })

    }



    private fun handleOnTokenUpdateClicked() {
        val url = "https://oauth.vk.com/authorize?client_id=6121396&scope=photos,video,messages,offline&redirect_uri=https://oauth.vk.com/blank.html&response_type=token&display=page"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun handleOnBtnSaveAlbumClicked() {

        Log.d("sislok", "Прошло1")
        photosUrls.clear()

        val clienteres = OkHttpClient()


            var urik = "https://api.vk.com/method/photos.get?owner_id=$ownerIDGlobal&album_id=$idAlbumSelected&access_token=$tokenGlobal&v=5.199"
            var urikL = urik

            val requesters = Request.Builder().url(urikL).build()

            clienteres.newCall(requesters).enqueue(object : okhttp3.Callback {

                override fun onResponse(call: Call, response: Response) {
                    val bodyros = response.body?.string()
                    Log.d("sislok", bodyros.toString())
                    val gson = GsonBuilder().create()
                    val responseros = gson.fromJson(bodyros, AlbumPhotos::class.java)
                    val urlPattern = "url=(https?:\\/\\/[^\\s)]+)".toRegex()
                    var inputedString = responseros.toString()
                    val urlsis = urlPattern.findAll(inputedString).map { it.groupValues[1] }.toList()
                    println("Extracted URLs:")
                    urlsis.forEach { photosUrls.add(it); println(it) }
                    saveUrlsToTxt(photosUrls)
                }
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("sislok", "Ошибка запроса")

                }

            })
    }


    override fun onClick(p0: View?) {

    }


    private fun saveUrlsToTxt(photosUrls: List<String>) {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val file = File(downloadsDir, "photos_saved.txt")

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

    private fun addItemsToSpinner(newItems: List<String>) {
        defaultAlbums.addAll(newItems)
        spinnerAdapter.notifyDataSetChanged()
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

