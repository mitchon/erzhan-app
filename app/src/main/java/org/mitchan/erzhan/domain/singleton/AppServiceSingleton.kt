package org.mitchan.erzhan.domain.singleton

import androidx.fragment.app.FragmentActivity
import org.mitchan.erzhan.domain.model.ISingleton

class AppServiceSingleton private constructor(context: FragmentActivity) : ISingleton {
    val fragmentActivity: FragmentActivity = context

    companion object : SingletonHolder<AppServiceSingleton, FragmentActivity>(::AppServiceSingleton)
}