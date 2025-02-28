package ru.success.road_to_success

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface {
    @get:GET("posts")
//    @get:GET("method/messages.getHistoryAttachments?peer_id=143274101&count=1&cmid=227218&attachment_types=photo&access_token=vk1.a.zVehBTs4fVQyS90D9qvac3qjUiUDwarEbIPBkT9di03SHsj0jz5WFjv8XwvoHQAE2RmUhmbNduJa6ND7LOPRP392SANhsLJWlHKskOnG5csu3U5TZ5dcVybcXXssXFsql0N9rZrRR_TSTkvhR4UP5rMrm6gIR5IMO6oDr0C1VUb4UeSN4TGjRMcNmRNHcu_LqU3lMRVtzJR_t6t2_MIDiw&v=5.199")

    val posts : Call<List<PostModel?>?>?
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
//        const val BASE_URL = "https://api.vk.com"
    }
    }