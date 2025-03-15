package ru.success.road_to_success.DTO.PhotoAlbums

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface PhotoAlbumsItemInterface {

    @GET("photos.getAlbums?need_covers=1&need_system=1&v=5.199")
    fun getAlbums(
        @Query("user_id")
        userId: String,
        @Query("access_token")
        accessToken: String

    ): Call<PhotoAlbumItems>


}