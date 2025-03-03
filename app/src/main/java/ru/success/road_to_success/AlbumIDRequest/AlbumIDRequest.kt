package com.example.example

import com.google.gson.annotations.SerializedName


data class AlbumIDRequest (

  @SerializedName("response" ) var response : Responses? = Responses()

)