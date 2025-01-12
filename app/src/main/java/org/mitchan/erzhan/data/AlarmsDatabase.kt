package org.mitchan.erzhan.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [AlarmEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AlarmsDatabase: RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    companion object {
        @Volatile
        private var instance: AlarmsDatabase? = null

        fun getInstance(context: Context): AlarmsDatabase {
            return instance ?: synchronized(this) {
                Room
                    .databaseBuilder(context, AlarmsDatabase::class.java, "alarms_db")
                    .build()
                    .also { instance = it }
            }
        }
    }
}