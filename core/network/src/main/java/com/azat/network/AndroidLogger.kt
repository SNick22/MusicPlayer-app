package com.azat.network

import android.util.Log
import io.ktor.client.plugins.logging.Logger

class AndroidLogger: Logger {

    override fun log(message: String) {
        Log.d("Ktor Logging", message)
    }
}
