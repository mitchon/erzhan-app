package org.mitchan.erzhan.domain.database.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import org.mitchan.erzhan.domain.database.model.alarm.Alarm
import java.util.UUID

class Converters {
    @TypeConverter
    fun uuidFromString(value: String?): UUID? {
        return value?.let { UUID.fromString(value) }
    }

    @TypeConverter
    fun uuidToString(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun alarmFromString(value: String?): Alarm? {
        return value?.let { Json.decodeFromString(it) }
    }

    @TypeConverter
    fun alarmToString(alarm: Alarm?): String? {
        return alarm?.let { Json.encodeToString(it) }
    }
}