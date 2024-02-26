package com.azat.common_impl

import android.util.Log
import com.azat.common.Logger

class AndroidLogger: Logger {

    override fun log(message: String) {
        Log.d("MusicApp Logger", message)
    }

    override fun err(exception: Throwable, message: String?) {
        Log.e("MusicApp Error", message, exception)
    }
}
