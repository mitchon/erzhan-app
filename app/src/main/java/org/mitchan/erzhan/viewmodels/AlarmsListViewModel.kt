package org.mitchan.erzhan.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mitchan.erzhan.models.AlarmListItemModel
import org.mitchan.erzhan.models.AlarmsListModel
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime
import java.util.UUID
import kotlin.random.Random

class AlarmsListViewModel: IViewModel<AlarmsListModel>(::AlarmsListModel) {
    fun add() {
        val new = AlarmListItemModel(
            id = UUID.randomUUID(),
            time = LocalTime.now() + Duration.ofMinutes(Random.nextLong() % 10 ),
            enabled = true,
            trait = AlarmListItemModel.TraitByWeekday(
                weekDayMap = mapOf(
                    DayOfWeek.MONDAY to true,
                    DayOfWeek.TUESDAY to false,
                    DayOfWeek.WEDNESDAY to true,
                )
            )
        )

        viewModelScope.launch(Dispatchers.IO) {
            stateFlow.update {
                it.copy(
                    items = it.items + (new.id to new)
                )
            }
        }
    }

    fun enableToggled(id: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            stateFlow.value.items[id]?.let { item ->
                stateFlow.update {
                    it.copy(
                        items = it.items + (item.id to item.copy(enabled = !item.enabled))
                    )
                }
            }
        }
    }

    fun delete(id: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            stateFlow.update {
                it.copy(
                    items = it.items - id
                )
            }
        }
    }
}