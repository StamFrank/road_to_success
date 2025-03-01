package com.example.example

import com.google.gson.annotations.SerializedName


data class Profiles (

  @SerializedName("id"                ) var id              : Int?        = null,
  @SerializedName("sex"               ) var sex             : Int?        = null,
  @SerializedName("screen_name"       ) var screenName      : String?     = null,
  @SerializedName("photo_50"          ) var photo50         : String?     = null,
  @SerializedName("photo_100"         ) var photo100        : String?     = null,
  @SerializedName("online_info"       ) var onlineInfo      : OnlineInfo? = OnlineInfo(),
  @SerializedName("online"            ) var online          : Int?        = null,
  @SerializedName("first_name"        ) var firstName       : String?     = null,
  @SerializedName("last_name"         ) var lastName        : String?     = null,
  @SerializedName("can_access_closed" ) var canAccessClosed : Boolean?    = null,
  @SerializedName("is_closed"         ) var isClosed        : Boolean?    = null

)