package com.example.example

import com.google.gson.annotations.SerializedName


data class Attachment (

  @SerializedName("type"  ) var type  : String? = null,
  @SerializedName("photo" ) var photo : Photo?  = Photo()

)