package org.mitchan.erzhan.models

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.UUID

data class AlarmModel(
    val id: UUID,
    val time: LocalTime,
    val enabled: Boolean,
    val trait: Trait
) {
    @Serializable
    @Polymorphic
    sealed class Trait

    @Serializable
    @SerialName("once")
    data object TraitOnce : Trait()
    @Serializable
    @SerialName("everyday")
    data object TraitEveryday: Trait()
    @Serializable
    @SerialName("by-weekday")
    data class TraitByWeekday(
        val weekDayMap: Map<DayOfWeek, Boolean>
    ): Trait()

    companion object {
        fun AlarmModel.toListItem(): AlarmListItemModel {
            return AlarmListItemModel(
                id = this.id,
                time = this.time,
                enabled = this.enabled,
                trait = this.trait,
            )
        }
    }
}
