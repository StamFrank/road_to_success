package ru.success.road_to_success.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.success.road_to_success.AdaptersAndClasses.Docs.DocsItems
import ru.success.road_to_success.DTO.Docs.DocsItemsCount
import ru.success.road_to_success.DTO.Docs.DocsItemsCountInterface
import ru.success.road_to_success.databinding.FragmentDocsBinding


class DocsFragment : Fragment() {

    private lateinit var binding: FragmentDocsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCredentials()

        //Попытки загружать только фрагменты где их не 0(у меня есть в вк раздел с одним - так что проверка на 1)

                for (i in 0..7) {
                    getDocsItemsCount(allCountData[i].docsType,ACCESS_TOKEN) { docsItemsCounter ->
                        if (docsItemsCounter != null) {
                            TYPE_NUMBER = docsItemsCounter
                            allCountData[i].docsSize = TYPE_NUMBER
                            Log.d("sislok", TYPE_NUMBER)
                            Log.d("sislok", allCountData.toString())
                        } else if (docsItemsCounter == "1"){
                            allCountData.removeAt(i)
                            println("Failed to get count")
                        }
                    }
                }
                Log.d("sislok", allCountData.toString())


//        println(updateDocsItemsList(ACCESS_TOKEN))




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDocsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.vk.com/method/")
            .build()
    }

    private fun getDocsItemsCount(type: String, accessToken: String, callback: (String?) -> Unit) {
        val retrofit = createRetrofit()
        val docsCountService = retrofit.create(DocsItemsCountInterface::class.java)

        docsCountService.getDocsItemsCount(type, accessToken).enqueue(object : Callback<DocsItemsCount> {
            override fun onResponse(call: Call<DocsItemsCount>, response: Response<DocsItemsCount>) {
                if (response.isSuccessful) {
                    val docsItemsCounter = response.body()?.response?.count
                    callback(docsItemsCounter)
                } else {
                    Log.e("API_ERROR", "Error: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<DocsItemsCount>, t: Throwable) {
                Log.e("API_ERROR", "Failure Call", t)
                callback(null)
            }
        })
    }






    private fun loadCredentials() {
        val sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        ACCESS_TOKEN = sharedPreferences.getString("access_token", "0").toString()
    }


    companion object {

        var ACCESS_TOKEN = ""
        var TYPE_NUMBER = ""

        var allCountData = mutableListOf(
            DocsItems("R.drawable.docs.png", "1" , "0"),
            DocsItems("R.drawable.archive.png", "2" , "0"),
            DocsItems("R.drawable.gif.png", "3" , "0"),
            DocsItems("R.drawable.image.png", "4" , "0"),
            DocsItems("R.drawable.audio.png", "5" , "0"),
            DocsItems("R.drawable.movie.png", "6" , "0"),
            DocsItems("R.drawable.e_book.png", "7" , "0"),
            DocsItems("R.drawable.unknown_document.png", "8", "0")
        )
    }
}