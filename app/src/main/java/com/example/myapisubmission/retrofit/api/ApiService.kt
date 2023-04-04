package com.example.myapisubmission.retrofit.api
import com.example.myapisubmission.retrofit.response.DetailResponse
import com.example.myapisubmission.retrofit.response.GitResponse
import com.example.myapisubmission.retrofit.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUsers(
        @Query("q") id: String
    ): Call<GitResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>

}
