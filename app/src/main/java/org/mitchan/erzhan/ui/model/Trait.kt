package org.mitchan.erzhan.ui.model

import kotlinx.serialization.Serializable
import java.time.DayOfWeek

@Serializable
data class Trait(
    val everyDay: Boolean = false,
    val weekDayMap: Map<DayOfWeek, Boolean> = emptyMap()
)
