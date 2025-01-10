package org.mitchan.erzhan.viewmodels

import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mitchan.erzhan.DatabaseInstance
import org.mitchan.erzhan.entities.AlarmsRepositoryImpl
import org.mitchan.erzhan.models.AlarmModel
import org.mitchan.erzhan.models.AlarmModel.Companion.toListItem
import org.mitchan.erzhan.models.AlarmsListModel
import org.mitchan.erzhan.routes.destinations.AlarmRouteDestination
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime
import java.util.UUID
import kotlin.random.Random

class AlarmsListViewModel: IViewModel<AlarmsListModel>(::AlarmsListModel) {

    private val alarmsRepository by lazy {
        AlarmsRepositoryImpl(DatabaseInstance.instance!!.alarmDao())
    }

    fun initialize() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = alarmsRepository.getAll().associateBy { it.id }

            stateFlow.update {
                it.copy(items = items)
            }
        }
    }

    fun add() {
        val model = AlarmModel(
            id = UUID.randomUUID(),
            time = LocalTime.now() + Duration.ofMinutes(Random.nextLong() % 10 ),
            enabled = true,
            trait = AlarmModel.TraitByWeekday(
                weekDayMap = mapOf(
                    DayOfWeek.MONDAY to true,
                    DayOfWeek.TUESDAY to false,
                    DayOfWeek.WEDNESDAY to true,
                )
            )
        )

        viewModelScope.launch(Dispatchers.IO) {
            val new = alarmsRepository.insert(model).toListItem()

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
            alarmsRepository.delete(id)
            stateFlow.update {
                it.copy(
                    items = it.items - id
                )
            }
        }
    }

    fun navigateItem(id: UUID, navigator: DestinationsNavigator) {
        navigator.navigate(AlarmRouteDestination(id))
    }
}