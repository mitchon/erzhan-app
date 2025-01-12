package org.mitchan.erzhan

import android.app.Application
import android.content.Context
import org.mitchan.erzhan.data.AlarmsDatabase
import org.mitchan.erzhan.data.AlarmsRepository
import org.mitchan.erzhan.data.AlarmsRepositoryImpl

class ErzhanApplication: Application() {
    lateinit var container: ApplicationContainer

    override fun onCreate() {
        super.onCreate()
        container = ApplicationContainerImpl(this)
    }
}

interface ApplicationContainer {
    val alarmsRepository: AlarmsRepository
}

class ApplicationContainerImpl(private val context: Context): ApplicationContainer {
    override val alarmsRepository: AlarmsRepository by lazy {
        AlarmsRepositoryImpl(AlarmsDatabase.getInstance(context).alarmDao())
    }
}

