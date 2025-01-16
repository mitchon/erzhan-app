package org.mitchan.erzhan.domain.database.model.alarm

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mitchan.erzhan.ui.pages.alarm.AlarmModel
import java.util.UUID

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey
    val id: UUID,
    val value: Alarm,
)
