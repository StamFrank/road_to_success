package com.example.example

import com.google.gson.annotations.SerializedName


data class Photo (

  @SerializedName("album_id"       ) var albumId      : Int?             = null,
  @SerializedName("date"           ) var date         : Int?             = null,
  @SerializedName("id"             ) var id           : Int?             = null,
  @SerializedName("owner_id"       ) var ownerId      : Int?             = null,
  @SerializedName("access_key"     ) var accessKey    : String?          = null,
  @SerializedName("sizes"          ) var sizes        : ArrayList<Sizes> = arrayListOf(),
  @SerializedName("text"           ) var text         : String?          = null,
  @SerializedName("web_view_token" ) var webViewToken : String?          = null,
  @SerializedName("has_tags"       ) var hasTags      : Boolean?         = null,
  @SerializedName("orig_photo"     ) var origPhoto    : OrigPhoto?       = OrigPhoto()

)