package ru.success.road_to_success.DTO.PhotoAlbums

import com.google.gson.annotations.SerializedName

data class PhotoAlbumItems(
    @SerializedName("response") val response: AlbumItemsResponse
)

data class AlbumItemsResponse(
    @SerializedName("items") val items: List<AlbumItemsItem>
)

data class AlbumItemsItem(
    @SerializedName("thumb_src") val thumbSrc: String,
    @SerializedName("title") val title: String,
    @SerializedName("id") val id: Int,
    @SerializedName("size") val size: Int
)
