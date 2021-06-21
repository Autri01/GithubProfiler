package com.example.github_user

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("users")
    suspend fun getUser():Response<List<User>>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: String?):Response<User>

    @GET("search/users")
    suspend fun searchUser(@Query("q")query: String):Response<UserResponse>
}