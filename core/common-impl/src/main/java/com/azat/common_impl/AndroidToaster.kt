package com.azat.common_impl

import android.content.Context
import android.widget.Toast
import com.azat.common.Toaster

class AndroidToaster(
    private val appContext: Context
): Toaster {

    override fun showToast(message: String) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT)
            .show()
    }
}
