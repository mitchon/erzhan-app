package org.mitchan.erzhan.ui.pages.alarmslist

import org.mitchan.erzhan.domain.database.model.alarm.Alarm
import java.util.UUID

data class AlarmsListModel(
    val items: Map<UUID, Alarm> = emptyMap(),
    val isInitialized: Boolean = false,
)
