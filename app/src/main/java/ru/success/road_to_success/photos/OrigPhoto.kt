package com.example.example

import com.google.gson.annotations.SerializedName


data class OrigPhoto (

  @SerializedName("height" ) var height : Int?    = null,
  @SerializedName("type"   ) var type   : String? = null,
  @SerializedName("url"    ) var url    : String? = null,
  @SerializedName("width"  ) var width  : Int?    = null

)