package com.example.example

import com.google.gson.annotations.SerializedName


data class Photo (

  @SerializedName("orig_photo"     ) var origPhoto    : OrigPhoto?       = OrigPhoto()

)