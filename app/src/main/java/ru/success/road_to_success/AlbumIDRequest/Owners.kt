package com.example.example

import com.google.gson.annotations.SerializedName


data class Owners (

  @SerializedName("allowed"  ) var allowed  : ArrayList<String> = arrayListOf(),
  @SerializedName("excluded" ) var excluded : ArrayList<String> = arrayListOf()

)