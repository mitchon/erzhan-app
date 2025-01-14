package org.mitchan.erzhan.domain.singleton

import android.content.Context
import org.mitchan.erzhan.domain.model.ISingleton

class AppServiceSingleton private constructor(context: Context) : ISingleton {
    val context: Context = context

    companion object : SingletonHolder<AppServiceSingleton, Context>(::AppServiceSingleton)
}