package ru.success.road_to_success.DTO.Docs

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DocsItemsCountInterface {
    @GET("docs.get?v=5.199&count=2000")
    fun getDocsItemsCount(
        @Query("type")
        userId: String,
        @Query("access_token")
        accessToken: String

    ): Call<DocsItemsCount>
}