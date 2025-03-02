package com.example.example

import com.google.gson.annotations.SerializedName


data class Itemsis (

  @SerializedName("date"                    ) var date                  : Int?              = null,
  @SerializedName("from_id"                 ) var fromId                : Int?              = null,
  @SerializedName("id"                      ) var id                    : Int?              = null,
  @SerializedName("version"                 ) var version               : Int?              = null,
  @SerializedName("out"                     ) var out                   : Int?              = null,
  @SerializedName("important"               ) var important             : Boolean?          = null,
  @SerializedName("is_hidden"               ) var isHidden              : Boolean?          = null,
  @SerializedName("attachments"             ) var attachments           : ArrayList<String> = arrayListOf(),
  @SerializedName("conversation_message_id" ) var conversationMessageId : Int?              = null,
  @SerializedName("fwd_messages"            ) var fwdMessages           : ArrayList<String> = arrayListOf(),
  @SerializedName("text"                    ) var text                  : String?           = null,
  @SerializedName("peer_id"                 ) var peerId                : Int?              = null,
  @SerializedName("random_id"               ) var randomId              : Int?              = null

)