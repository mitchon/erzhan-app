package org.mitchan.erzhan.viewmodels

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mitchan.erzhan.entities.AlarmsDatabase
import org.mitchan.erzhan.entities.AlarmsRepository
import org.mitchan.erzhan.entities.AlarmsRepositoryImpl
import org.mitchan.erzhan.models.AlarmListItemModel
import org.mitchan.erzhan.models.AlarmsListModel
import org.mitchan.erzhan.routes.AlarmRoute
import org.mitchan.erzhan.routes.destinations.AlarmRouteDestination
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime
import java.util.UUID
import kotlin.random.Random

class AlarmsListViewModel(
    private val alarmsRepository: AlarmsRepository
): IViewModel<AlarmsListModel>(::AlarmsListModel) {

    fun initialize() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = alarmsRepository.getAll().map {
                AlarmListItemModel(
                    id = it.id,
                    time = it.time,
                    enabled = it.enabled,
//                    trait = it.trait,
                    trait = AlarmListItemModel.TraitByWeekday(mapOf()),
                )
            }.associateBy { it.id }

            stateFlow.update {
                it.copy(items = items)
            }
        }
    }

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

    fun navigateAlarm(id: UUID, navigator: DestinationsNavigator) {
        navigator.navigate(AlarmRouteDestination(id))
    }
}