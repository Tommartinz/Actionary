package com.tomartin.actionary.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomartin.actionary.data.repositories.SettingsRepository
import com.tomartin.actionary.utils.settings.ApplicationTheme
import com.tomartin.actionary.utils.state.SettingsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {
    private val _settingsUiState = MutableStateFlow(SettingsUIState())
    val settingsUIState: StateFlow<SettingsUIState> = _settingsUiState.asStateFlow()

    suspend fun getDeleteTasksWhenCompleted() {
        settingsRepository.getDeleteTasksWhenCompleted().collect { option ->
            _settingsUiState.value = _settingsUiState.value.copy(
                deleteTasksWhenCompleted = option
            )
        }
    }

    fun setDeleteTasksWhenCompleted(deleteTasksWhenCompleted: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDeleteTasksWhenCompleted(deleteTasksWhenCompleted)
        }
    }

    suspend fun getApplicationTheme() {
        settingsRepository.getApplicationTheme().collect { theme ->
            _settingsUiState.value = _settingsUiState.value.copy(
                uiTheme = ApplicationTheme.valueOf(theme)
            )
        }
    }

    fun setApplicationTheme(applicationTheme: ApplicationTheme) {
        viewModelScope.launch {
            settingsRepository.setApplicationTheme(applicationTheme)
        }
    }
}