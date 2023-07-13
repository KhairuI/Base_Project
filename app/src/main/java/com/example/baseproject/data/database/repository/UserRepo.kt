package com.example.baseproject.data.database.repository


interface UserRepo {

    suspend fun insert(user: User)

    suspend fun delete()

    fun load(): List<User>
}