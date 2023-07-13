package com.example.baseproject.data.database.repository

import javax.inject.Inject

class UserRepository @Inject constructor(private val dao: UserDao) : UserRepo {

    override suspend fun insert(user: User) {
        dao.insert(user)
    }

    override suspend fun delete() {
        dao.delete()
    }

    override fun load(): List<User> {
        return dao.load()
    }
}