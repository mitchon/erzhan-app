package org.mitchan.erzhan.models

import java.time.LocalTime
import java.util.UUID

data class AlarmsListModel(
    val items: Map<UUID, AlarmListItemModel> = emptyMap()
)

data class AlarmListItemModel(
    val id: UUID,
    val time: LocalTime,
    val enabled: Boolean,
    val trait: AlarmModel.Trait
)
