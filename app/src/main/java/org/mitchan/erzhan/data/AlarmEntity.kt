package org.mitchan.erzhan.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.TypeConverter
import androidx.room.Update
import kotlinx.serialization.json.Json
import org.mitchan.erzhan.data.AlarmEntity.Companion.toListItem
import org.mitchan.erzhan.data.AlarmEntity.Companion.toModel
import org.mitchan.erzhan.ui.pages.alarmslist.AlarmListItemModel
import org.mitchan.erzhan.ui.pages.alarm.AlarmModel
import org.mitchan.erzhan.ui.pages.alarm.AlarmModel.Trait
import java.time.LocalTime
import java.util.UUID

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey
    val id: UUID,
    val time: LocalTime,
    val enabled: Boolean,
    val trait: Trait
) {
    companion object {
        fun fromModel(model: AlarmModel): AlarmEntity {
            return AlarmEntity(
                id = model.id,
                time = model.time,
                enabled = model.enabled,
                trait = model.trait,
            )
        }

        fun AlarmEntity.toModel(): AlarmModel {
            return AlarmModel(
                id = this.id,
                time = this.time,
                enabled = this.enabled,
                trait = this.trait,
            )
        }

        fun AlarmEntity.toListItem(): AlarmListItemModel {
            return AlarmListItemModel(
                id = this.id,
                time = this.time,
                enabled = this.enabled,
                trait = this.trait,
            )
        }
    }
}
