package ru.success.road_to_success.DTO.Movie

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAlbumItemsInterface {

    @GET("video.getAlbums?extended=1&need_system=1&v=5.199")
    fun getMovies(
    @Query("access_token")
    accessToken: String

): Call<MovieAlbumItems>


}