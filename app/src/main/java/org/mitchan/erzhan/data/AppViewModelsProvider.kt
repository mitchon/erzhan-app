package org.mitchan.erzhan.data

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import org.mitchan.erzhan.ErzhanApplication
import org.mitchan.erzhan.ui.alarm.AlarmViewModel
import org.mitchan.erzhan.ui.alarmslist.AlarmsListViewModel

object AppViewModelsProvider {
    val Factory = viewModelFactory {
        initializer {
            AlarmViewModel(
                erzhanApplication().container.alarmsRepository
            )
        }

        initializer {
            AlarmsListViewModel(
                erzhanApplication().container.alarmsRepository
            )
        }
    }
}

fun CreationExtras.erzhanApplication(): ErzhanApplication = (this[AndroidViewModelFactory.APPLICATION_KEY] as ErzhanApplication)