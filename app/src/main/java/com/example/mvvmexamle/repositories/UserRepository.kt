package com.example.mvvmexamle.repositories

import com.example.mvvmexamle.database.AppDatabase
import com.example.mvvmexamle.database.entity.User
import com.example.mvvmexamle.network.ApiService

class UserRepository(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) {

    suspend fun getRemoteUsers() = apiService.getUsers()

    suspend fun getLocalUsers() = appDatabase.userDao().getAllUsers()

    suspend fun addUsers(list: List<User>) = appDatabase.userDao().addUsers(list)


}