package ru.success.road_to_success.DTO.Movie

import com.google.gson.annotations.SerializedName

class MovieAlbumItems (
    @SerializedName("response") val response: MovieItemsResponse
)

data class MovieItemsResponse(
    @SerializedName("items") val items: List<MovieItemsItem>
)

data class MovieItemsItem(
    @SerializedName("id") val id: String,
    @SerializedName("count") val count: String,
    @SerializedName("title") val title: String,
    @SerializedName("image") val items: List<MovieImage>
)
data class MovieImage(
    @SerializedName("url") val url: String,
)
