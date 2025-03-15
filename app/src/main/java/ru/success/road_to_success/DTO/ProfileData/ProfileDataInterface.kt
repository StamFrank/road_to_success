package ru.success.road_to_success.DTO.ProfileData

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileDataInterface {

    @GET("users.get?fields=photo_400_orig&v=5.199")
    fun getProfileData(
    @Query("access_token")
    accessToken: String
    ): Call<ProfileDataItems>

}