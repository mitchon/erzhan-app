package org.mitchan.erzhan

import android.app.Application
import org.mitchan.erzhan.domain.singleton.AppServiceSingleton
import org.mitchan.erzhan.domain.singleton.RoomSingleton

class ErzhanApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppServiceSingleton.getInstance(this.applicationContext)
        RoomSingleton.getInstance(Unit)
    }
}

