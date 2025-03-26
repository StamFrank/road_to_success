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
import ru.success.road_to_success.AdaptersAndClasses.Movie.MovieAdapter
import ru.success.road_to_success.AdaptersAndClasses.Movie.MovieItems
import ru.success.road_to_success.DTO.Movie.MovieAlbumItems
import ru.success.road_to_success.DTO.Movie.MovieAlbumItemsInterface
import ru.success.road_to_success.databinding.FragmentMovieBinding


class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        loadCredentials()
        fetchMovieAlbums()

    }

    private fun fetchMovieAlbums() {
        val retrofit = createRetrofit()
        val retrofiter = retrofit.create(MovieAlbumItemsInterface::class.java)
        retrofiter.getMovies(ACCESS_TOKEN).enqueue(object : Callback<MovieAlbumItems> {
            override fun onResponse(call: Call<MovieAlbumItems>, response: Response<MovieAlbumItems>) {
                if (response.isSuccessful) {
                    val movieAlbumItems = response.body()?.response?.items ?: emptyList()

                    val movieItemsList = movieAlbumItems.mapNotNull { movieItem ->

                        val images = movieItem.items
                        if (!images.isNullOrEmpty() && images.size > 1) {

                            val imageUrl = images[1].url
                            MovieItems(
                                movieAlbumImageUrl = imageUrl,
                                movieAlbumName = movieItem.title,
                                movieAlbumId = "ID: ${movieItem.id}",
                                movieItemsCount = movieItem.count
                            )
                        } else {
                            null
                        }
                    }

                    movieAdapter = MovieAdapter(movieItemsList)
                    binding.recyclerViewMovie.adapter = movieAdapter
                } else {
                    Log.e("API_ERROR", "Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieAlbumItems>, t: Throwable) {
                Log.e("API_ERROR", "Failure Call", t)
            }
        })
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initRecyclerView() {
        binding.recyclerViewMovie.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun loadCredentials() {
        val sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        ACCESS_TOKEN = sharedPreferences.getString("access_token", "0").toString()
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
        var ACCESS_TOKEN = ""

    }



}