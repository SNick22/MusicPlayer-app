package com.azat.common_impl

import android.content.Context
import com.azat.common.Resources

class AndroidResources(
    private val appContext: Context
): Resources {

    override fun getString(id: Int): String = appContext.getString(id)

    override fun getString(id: Int, vararg placeholders: Any): String =
        appContext.getString(id, placeholders)
}
