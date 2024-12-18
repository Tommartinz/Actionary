package com.tomartin.actionary.data.repositories

import com.tomartin.actionary.data.database.Task
import com.tomartin.actionary.data.database.TasksDatabase
import com.tomartin.actionary.utils.settings.TypeOrder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksRepository @Inject constructor(private val tasksDatabase: TasksDatabase) {

    suspend fun createTask(task: Task) = tasksDatabase.taskController().insertTask(task)

    fun getAllTasks(typeOrder: TypeOrder, isAscending: Boolean): Flow<List<Task>> {
        return when (typeOrder) {
            TypeOrder.NAME -> {
                tasksDatabase.taskController().queryAllTasksByName(isAscending)
            }

            TypeOrder.DATE_ADDED -> {
                tasksDatabase.taskController().getAllTasksByDateAdded(isAscending)
            }
        }
    }

    fun getTaskById(id: Int) = tasksDatabase.taskController().queryTaskById(id)

    suspend fun updateTask(task: Task) = tasksDatabase.taskController().updateTask(task)

    suspend fun deleteTask(task: Task) = tasksDatabase.taskController().deleteTask(task)
}