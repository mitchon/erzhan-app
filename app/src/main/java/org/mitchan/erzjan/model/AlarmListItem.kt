package org.mitchan.erzhan.model

import java.time.DayOfWeek
import java.time.LocalTime
import java.util.UUID

data class AlarmListItem(
    val id: UUID,
    val time: LocalTime,
    val enabled: Boolean,
    val trait: Trait
) {
    enum class TraitType { ONCE, EVERYDAY, BY_WEEKDAY }
    sealed class Trait(val type: TraitType)

    class TraitOnce: Trait(TraitType.ONCE)
    class TraitEveryday: Trait(TraitType.EVERYDAY)
    data class TraitByWeekday(
        val weekDayMap: Map<DayOfWeek, Boolean>
    ): Trait(TraitType.BY_WEEKDAY)
}
