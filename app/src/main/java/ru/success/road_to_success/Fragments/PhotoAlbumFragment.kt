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
import ru.success.road_to_success.AdaptersAndClasses.PhotoAlbums.AlbumAdapter
import ru.success.road_to_success.AdaptersAndClasses.PhotoAlbums.AlbumItems
import ru.success.road_to_success.DTO.PhotoAlbums.PhotoAlbumItems
import ru.success.road_to_success.DTO.PhotoAlbums.PhotoAlbumsItemInterface
import ru.success.road_to_success.databinding.FragmentPhotoAlbumBinding


class PhotoAlbumFragment : Fragment() {

    private var _binding: FragmentPhotoAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var albumAdapter: AlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        loadCredentials()
        fetchAlbums()
    }

    private fun initRecyclerView() {
        binding.recyclerViewPhoto.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun loadCredentials() {
        val sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        ACCESS_TOKEN = sharedPreferences.getString("access_token", "0").toString()
        USER_ID = sharedPreferences.getString("user_id", "0").toString()
    }

    private fun fetchAlbums() {
        val retrofit = createRetrofit()
        val retrofiter = retrofit.create(PhotoAlbumsItemInterface::class.java)
        retrofiter.getAlbums(USER_ID, ACCESS_TOKEN).enqueue(object : Callback<PhotoAlbumItems> {
            override fun onResponse(call: Call<PhotoAlbumItems>, response: Response<PhotoAlbumItems>) {
                if (response.isSuccessful) {
                    val photoAlbumItems = response.body()?.response?.items ?: emptyList()
                    val albumItemsList = photoAlbumItems
                        .filter { albumItem -> albumItem.size > 0 }
                        .map { albumItem ->
                            AlbumItems(
                                profilePhotoUrl = albumItem.thumbSrc,
                                name = albumItem.title,
                                id = "ID: ${albumItem.id}",
                                itemCount = albumItem.size
                            )
                        }

                    albumAdapter = AlbumAdapter(albumItemsList)
                    binding.recyclerViewPhoto.adapter = albumAdapter
                } else {
                    Log.e("API_ERROR", "Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PhotoAlbumItems>, t: Throwable) {
                Log.e("API_ERROR", "Failure Call", t)
            }
        })
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.vk.com/method/")
            .build()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var USER_ID = ""
        var ACCESS_TOKEN = ""

        fun newInstance() = PhotoAlbumFragment()
    }
}