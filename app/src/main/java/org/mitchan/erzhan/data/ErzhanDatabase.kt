package org.mitchan.erzhan.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [AlarmEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ErzhanDatabase: RoomDatabase() {
    abstract fun alarms(): AlarmsDao

    companion object {
        @Volatile
        private var instance: ErzhanDatabase? = null

        fun getInstance(context: Context): ErzhanDatabase {
            return instance ?: synchronized(this) {
                Room
                    .databaseBuilder(context, ErzhanDatabase::class.java, "erzhan_db")
                    .build()
                    .also { instance = it }
            }
        }
    }
}