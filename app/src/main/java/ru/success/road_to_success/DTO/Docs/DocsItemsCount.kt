package ru.success.road_to_success.DTO.Docs

import com.google.gson.annotations.SerializedName

data class DocsItemsCount(
    @SerializedName("response") val response: DocsItemsCountResponse
)

data class DocsItemsCountResponse(
    @SerializedName("count") val count: String
)
