package ru.success.road_to_success.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.success.road_to_success.AdaptersAndClasses.Docs.DocsAdapter
import ru.success.road_to_success.AdaptersAndClasses.Docs.DocsItems
import ru.success.road_to_success.AdaptersAndClasses.Docs.DocsNaUrItems
import ru.success.road_to_success.DTO.Docs.DocsItemsCount
import ru.success.road_to_success.DTO.Docs.DocsItemsCountInterface
import ru.success.road_to_success.databinding.FragmentDocsBinding


class DocsFragment : Fragment() {

    private lateinit var binding: FragmentDocsBinding
    private lateinit var docsAdapter: DocsAdapter
    private var docsItemsList: MutableList<DocsItems> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        loadCredentials()
        getDocsItems()


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

    private fun getDocsItems() {
        for (i in 0 until allCountData.size) {

            val retrofit = createRetrofit()
            val docsCountService = retrofit.create(DocsItemsCountInterface::class.java)

            docsCountService.getDocsItemsCount((i+1).toString(), ACCESS_TOKEN).enqueue(object : Callback<DocsItemsCount> {
                override fun onResponse(call: Call<DocsItemsCount>, response: Response<DocsItemsCount>) {
                    if (response.isSuccessful) {
                        val docsItemsCounter = response.body()?.response?.count?.toIntOrNull()

                        if (docsItemsCounter == null || docsItemsCounter == 0) {
                            Log.d("EmptyType", "Empty type or failed to get count")
                        } else {
                            val elementItem = allCountData[i]
                            docsItemsList.add(
                                DocsItems(
                                    docsImage = elementItem.docsImageUrl,
                                    docsType = elementItem.docsTypeName,
                                    docsSize = "Size: $docsItemsCounter"
                                )
                            )

                            // Уведомляем адаптер об изменениях\
                            docsAdapter = DocsAdapter(docsItemsList)
                            binding.recyclerViewDocs.adapter = docsAdapter
                        }

                    } else {
                        Log.e("API_ERROR", "Error: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DocsItemsCount>, t: Throwable) {
                    Log.e("API_ERROR", "Failure Call", t)
                }
            })
        }
    }



    private fun initRecyclerView() {
        binding.recyclerViewDocs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun loadCredentials() {
        val sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        ACCESS_TOKEN = sharedPreferences.getString("access_token", "0").toString()
    }


    companion object {

        var ACCESS_TOKEN = ""
        var TYPE_NUMBER = ""

        //list with name of ImageFiels and name of Items in Recycler
        var allCountData = mutableListOf(
            DocsNaUrItems("docs", "Text Document" ),
            DocsNaUrItems("archive", "Archive"),
            DocsNaUrItems("gif", "GIF" ),
            DocsNaUrItems("image", "Image"),
            DocsNaUrItems("audio", "Audio"),
            DocsNaUrItems("movie", "Movie"),
            DocsNaUrItems("e_book", "E-book"),
            DocsNaUrItems("unknown_document", "Unknown Document")
        )
    }
}