package ru.success.road_to_success.DTO.Chat

import com.google.gson.annotations.SerializedName

data class ChatId(
    @SerializedName("response") val response: ChatIdResponse
)
data class ChatIdResponse(
    @SerializedName("items") val items: List<ChatIdItems>
)
data class ChatIdItems(
    @SerializedName("conversation") val conversation: ChatIdConversation
)
data class ChatIdConversation(
    @SerializedName("peer") val peer: ChatIdPeer
)
data class ChatIdPeer(
    @SerializedName("id") val id: String
)
