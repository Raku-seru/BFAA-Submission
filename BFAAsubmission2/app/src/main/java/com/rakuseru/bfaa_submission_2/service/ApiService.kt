package com.rakuseru.bfaa_submission_2.service

import com.rakuseru.bfaa_submission_2.BuildConfig
import com.rakuseru.bfaa_submission_2.datasource.SearchResponse
import com.rakuseru.bfaa_submission_2.datasource.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    suspend fun getListUsersAsync(): ArrayList<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    suspend fun getDetailUserAsync(@Path("username") username: String): UserResponse

    @GET("search/users")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    fun getUserBySearch(@Query("q") username: String): Call<SearchResponse>

    @GET("users/{username}/repos")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    suspend fun getListRepository(@Path("username") username: String): ArrayList<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    suspend fun getListFollowers(@Path("username") username: String): ArrayList<UserResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    suspend fun getListFollowing(@Path("username") username: String): ArrayList<UserResponse>

    companion object {
        private const val API_TOKEN = BuildConfig.API_TOKEN
    }
}