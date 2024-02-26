package com.azat.database

interface TokenPrefs {

    fun putToken(token: String)

    fun getToken(): String?
}
