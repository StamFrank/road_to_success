package com.example.example

import com.google.gson.annotations.SerializedName


data class Responses (

  @SerializedName("count" ) var count : Int?             = null,
  @SerializedName("items" ) var items : ArrayList<Itemsis> = arrayListOf()

)