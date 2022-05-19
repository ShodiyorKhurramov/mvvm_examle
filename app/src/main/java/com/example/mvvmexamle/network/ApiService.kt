package com.example.mvvmexamle.network

import com.example.mvvmexamle.database.entity.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}