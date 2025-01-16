package org.mitchan.erzhan.ui.pages.alarm

import org.mitchan.erzhan.domain.database.model.alarm.Alarm

data class AlarmModel(
    val value: Alarm = Alarm(),
    val isInitialized: Boolean = false,
    val isNew: Boolean = true,
)
