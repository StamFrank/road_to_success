package com.example.example

import com.google.gson.annotations.SerializedName


data class Items (

  @SerializedName("attachment" ) var attachment : Attachment? = Attachment(),
  @SerializedName("date"       ) var date       : Int?        = null,
  @SerializedName("message_id" ) var messageId  : Int?        = null,
  @SerializedName("cmid"       ) var cmid       : Int?        = null,
  @SerializedName("from_id"    ) var fromId     : Int?        = null,
  @SerializedName("position"   ) var position   : Int?        = null

)