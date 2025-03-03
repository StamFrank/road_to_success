package com.example.example

import com.google.gson.annotations.SerializedName


data class Itemses (

  @SerializedName("id"                  ) var id               : Int?            = null,
  @SerializedName("owner_id"            ) var ownerId          : Int?            = null,
  @SerializedName("size"                ) var size             : Int?            = null,
  @SerializedName("title"               ) var title            : String?         = null,
  @SerializedName("feed_disabled"       ) var feedDisabled     : Int?            = null,
  @SerializedName("feed_has_pinned"     ) var feedHasPinned    : Int?            = null,
  @SerializedName("created"             ) var created          : Int?            = null,
  @SerializedName("description"         ) var description      : String?         = null,
  @SerializedName("can_delete"          ) var canDelete        : Boolean?        = null,
  @SerializedName("can_include_to_feed" ) var canIncludeToFeed : Boolean?        = null,
  @SerializedName("is_locked"           ) var isLocked         : Boolean?        = null,
  @SerializedName("privacy_comment"     ) var privacyComment   : PrivacyComment? = PrivacyComment(),
  @SerializedName("privacy_view"        ) var privacyView      : PrivacyView?    = PrivacyView(),
  @SerializedName("thumb_id"            ) var thumbId          : Int?            = null,
  @SerializedName("thumb_is_last"       ) var thumbIsLast      : Int?            = null,
  @SerializedName("updated"             ) var updated          : Int?            = null

)