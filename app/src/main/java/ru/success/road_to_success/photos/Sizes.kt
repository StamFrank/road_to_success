package com.example.example

import com.google.gson.annotations.SerializedName


data class Sizes (

  @SerializedName("height" ) var height : Int?    = null,
  @SerializedName("type"   ) var type   : String? = null,
  @SerializedName("width"  ) var width  : Int?    = null,
  @SerializedName("url"    ) var url    : String? = null

)