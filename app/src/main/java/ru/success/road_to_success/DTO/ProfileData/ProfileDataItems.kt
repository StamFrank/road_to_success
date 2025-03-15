package ru.success.road_to_success.DTO.ProfileData

import com.google.gson.annotations.SerializedName

data class ProfileDataItems(
    @SerializedName("response") val response: ArrayList<ProfileItemsResponse>
)

data class ProfileItemsResponse(
    @SerializedName("first_name") val firstname: String,
    @SerializedName("last_name") val lastname: String,
    @SerializedName("photo_400_orig") val photo400orig: String
)

