package org.mitchan.erzhan.data

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import org.mitchan.erzhan.ui.pages.alarm.AlarmModel.Trait
import java.time.LocalTime
import java.util.UUID

class Converters {
    @TypeConverter
    fun timeFromString(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(value) }
    }

    @TypeConverter
    fun timeToString(time: LocalTime?): String? {
        return time?.toString()
    }

    @TypeConverter
    fun uuidFromString(value: String?): UUID? {
        return value?.let { UUID.fromString(value) }
    }

    @TypeConverter
    fun uuidToString(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun traitFromString(value: String?): Trait? {
        return value?.let { Json.decodeFromString(it) }
    }

    @TypeConverter
    fun traitToString(trait: Trait?): String? {
        return trait?.let { Json.encodeToString(it) }
    }
}