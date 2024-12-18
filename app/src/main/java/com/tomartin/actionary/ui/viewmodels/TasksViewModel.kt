package com.tomartin.actionary.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomartin.actionary.data.database.Task
import com.tomartin.actionary.data.repositories.TasksRepository
import com.tomartin.actionary.data.repositories.SettingsRepository
import com.tomartin.actionary.utils.settings.SortOrder
import com.tomartin.actionary.utils.settings.TypeOrder
import com.tomartin.actionary.utils.state.TasksUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val settingsRepository: SettingsRepository
): ViewModel() {
    private val _tasksUiState = MutableStateFlow(TasksUIState())
    val tasksUiState: StateFlow<TasksUIState> = _tasksUiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllTasks()
            getTypeOrder()
            getSortOrder()
        }
    }

    fun createTask(task: Task) = viewModelScope.launch { tasksRepository.createTask(task) }

    fun getAllTasks() {
        viewModelScope.launch {
            tasksRepository.getAllTasks(tasksUiState.value.typeOrder, tasksUiState.value.sortOrder.value).collect { tasks ->
                _tasksUiState.value = _tasksUiState.value.copy(tasks = tasks)
            }
        }
    }

    fun getTypeOrder() {
        viewModelScope.launch {
            settingsRepository.getTypeOrder().collect { typeOrder ->
                _tasksUiState.value = _tasksUiState.value.copy(typeOrder = TypeOrder.valueOf(typeOrder))
            }
        }
    }

    fun setTypeOrder(typeOrder: TypeOrder) {
        viewModelScope.launch {
            settingsRepository.setTypeOrder(typeOrder)
        }
    }

    fun getSortOrder() {
        viewModelScope.launch {
            settingsRepository.getSortOrder().collect { sortOrder ->
                _tasksUiState.value = _tasksUiState.value.copy(sortOrder = SortOrder.valueOf(sortOrder))
            }
        }
    }

    fun setSortOrder(sortOrder: SortOrder) {
        viewModelScope.launch {
            settingsRepository.setSortOrder(sortOrder)
        }
    }

    fun getTaskById(id: Int): Flow<Task> = tasksRepository.getTaskById(id)

    fun updateTask(task: Task) = viewModelScope.launch { tasksRepository.updateTask(task) }

    fun deleteTask(task: Task) = viewModelScope.launch { tasksRepository.deleteTask(task) }
}