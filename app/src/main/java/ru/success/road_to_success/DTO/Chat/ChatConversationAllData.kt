package ru.success.road_to_success.DTO.Chat

import com.google.gson.annotations.SerializedName

data class ChatConversationAllData(
    @SerializedName("response") val response: ChatConversationAllDataResponse
)
data class ChatConversationAllDataResponse(
    @SerializedName("items") val items: List<ChatConversationAllDataItems>
)
data class ChatConversationAllDataItems(
    @SerializedName("peer") val peer: ChatConversationAllDataPeer,
    @SerializedName("chat_settings") val chatsettings: ChatConversationAllDataChatSettings,
    @SerializedName("last_conversation_message_id") val lastconversationmessageid: String
)
data class ChatConversationAllDataPeer(
    @SerializedName("id") val id: String
)
data class ChatConversationAllDataChatSettings(
    @SerializedName("title") val title: String,
    @SerializedName("id") val id: String,
    @SerializedName("photo") val photo: ChatConversationAllDataPhoto
)
data class ChatConversationAllDataPhoto(
    @SerializedName("photo_100") val photo100: String
)
