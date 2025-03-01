package com.example.example

import com.google.gson.annotations.SerializedName


data class OnlineInfo (

  @SerializedName("visible"   ) var visible  : Boolean? = null,
  @SerializedName("last_seen" ) var lastSeen : Int?     = null,
  @SerializedName("is_online" ) var isOnline : Boolean? = null,
  @SerializedName("app_id"    ) var appId    : Int?     = null,
  @SerializedName("is_mobile" ) var isMobile : Boolean? = null

)