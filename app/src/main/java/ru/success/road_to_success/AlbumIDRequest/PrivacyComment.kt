package com.example.example

import com.google.gson.annotations.SerializedName


data class PrivacyComment (

  @SerializedName("category" ) var category : String? = null,
  @SerializedName("lists"    ) var lists    : Lists?  = Lists(),
  @SerializedName("owners"   ) var owners   : Owners? = Owners()

)