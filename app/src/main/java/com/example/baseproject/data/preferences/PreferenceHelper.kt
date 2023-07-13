package com.example.baseproject.data.preferences

interface PreferenceHelper {

    fun isLoggedIn(): Boolean

    fun setLoggedIn(isLoggedIn: Boolean)

}