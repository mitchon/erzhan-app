package org.mitchan.erzhan.ui.alarmslist

import org.mitchan.erzhan.ui.alarm.AlarmModel
import java.time.LocalTime
import java.util.UUID

data class AlarmsListModel(
    val items: Map<UUID, AlarmListItemModel> = emptyMap(),
    val isInitialized: Boolean = false,
)

data class AlarmListItemModel(
    val id: UUID,
    val time: LocalTime,
    val enabled: Boolean,
    val trait: AlarmModel.Trait
)
