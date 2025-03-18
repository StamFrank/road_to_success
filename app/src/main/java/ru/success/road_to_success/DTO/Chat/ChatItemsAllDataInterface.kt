package ru.success.road_to_success.DTO.Chat

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatItemsAllDataInterface {

        @GET("messages.getConversationsById?v=5.199&extended=1&count=1")
        fun getChatItemsAllData(
            @Query("peer_ids")
            peerIds: String,
            @Query("access_token")
            accessToken: String

        ): Call<ChatItemsAllData>
}