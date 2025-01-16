package org.mitchan.erzhan.domain.singleton

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.mitchan.erzhan.domain.database.model.alarm.AlarmEntity
import org.mitchan.erzhan.domain.database.model.alarm.AlarmsDao
import org.mitchan.erzhan.domain.database.converters.Converters
import org.mitchan.erzhan.domain.model.ISingleton
import org.mitchan.erzhan.domain.singleton.RoomSingleton.Companion.buildDatabase

@Database(entities = [AlarmEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RoomSingleton protected constructor() : ISingleton, RoomDatabase() {
    abstract fun alarms(): AlarmsDao

    companion object : SingletonHolder<RoomSingleton, Unit>({ buildDatabase() }) {
        private fun buildDatabase(): RoomSingleton {
            val context: Context = AppServiceSingleton.getInstanceUnsafe().context

            return Room
                .databaseBuilder(
                    context = context,
                    klass = RoomSingleton::class.java,
                    name = "erzhan_db"
                )
                .build()
        }
    }
}