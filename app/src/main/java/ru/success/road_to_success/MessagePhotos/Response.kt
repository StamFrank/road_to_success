package com.example.example

import com.google.gson.annotations.SerializedName


data class Response (

  @SerializedName("items"    ) var items    : ArrayList<Items>    = arrayListOf()


)