package org.mitchan.erzhan.ui.model

import kotlinx.serialization.Serializable
import java.time.DayOfWeek

@Serializable
data class Trait(
    val once: Boolean = true,
    val everyDay: Boolean = false,
    val weekDayMap: Map<DayOfWeek, Boolean> = emptyMap()
)
