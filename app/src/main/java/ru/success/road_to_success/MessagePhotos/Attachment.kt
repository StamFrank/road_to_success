package com.example.example

import com.google.gson.annotations.SerializedName


data class Attachment (

  @SerializedName("photo" ) var photo : Photo?  = Photo()

)