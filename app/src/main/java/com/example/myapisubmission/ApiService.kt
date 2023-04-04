package com.example.myapisubmission
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
