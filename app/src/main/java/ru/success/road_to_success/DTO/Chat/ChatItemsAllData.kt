package ru.success.road_to_success.DTO.Chat

import com.google.gson.annotations.SerializedName

data class ChatItemsAllData(
    @SerializedName("response") val response: ChatItemsAllDataResponse
)
data class ChatItemsAllDataResponse(
    @SerializedName("profiles") val profiles: List<ChatItemsAllDataProfiles>,
    @SerializedName("items") val items: List<ChatItemsAllDataItems>
)
data class ChatItemsAllDataItems(
    @SerializedName("last_conversation_message_id") val lastconversationmessageid: String
)
data class ChatItemsAllDataProfiles(
    @SerializedName("photo_100") val photo100: String,
    @SerializedName("id") val id: String,
    @SerializedName("first_name") val firstname: String,
    @SerializedName("last_name") val lastname: String
)





