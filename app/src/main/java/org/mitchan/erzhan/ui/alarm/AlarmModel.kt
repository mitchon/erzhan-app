package org.mitchan.erzhan.ui.alarm

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.mitchan.erzhan.ui.alarmslist.AlarmListItemModel
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.UUID

data class AlarmModel(
    val id: UUID = UUID.randomUUID(),
    val time: LocalTime = LocalTime.now(),
    val enabled: Boolean = true,
    val trait: Trait = TraitEveryday,
    val isInitialized: Boolean = false,
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
