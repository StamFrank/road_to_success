package ru.success.road_to_success.DTO.Chat

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatIdInterface {

    @GET("messages.getConversations?v=5.199&count=200")
    fun getChatId(
        @Query("access_token")
        accessToken: String

    ): Call<ChatId>
}