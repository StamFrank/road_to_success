package com.example.example

import com.google.gson.annotations.SerializedName


data class Items (

  @SerializedName("attachment" ) var attachment : Attachment? = Attachment(),
  @SerializedName("cmid"       ) var cmid       : Int?        = null,
)