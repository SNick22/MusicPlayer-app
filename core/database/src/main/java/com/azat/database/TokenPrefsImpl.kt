package com.azat.database

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

internal class TokenPrefsImpl(
    private val sharedPreferences: SharedPreferences
): TokenPrefs {

    override fun putToken(token: String) {
        sharedPreferences.edit {
            putString(TOKEN_KEY, token)
        }
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    companion object {
        private const val TOKEN_KEY = "token"
    }
}
