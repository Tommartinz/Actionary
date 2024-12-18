package com.tomartin.actionary.utils.state

import com.tomartin.actionary.data.database.Task
import com.tomartin.actionary.utils.settings.SortOrder
import com.tomartin.actionary.utils.settings.TypeOrder

data class TasksUIState(
    val tasks: List<Task> = emptyList(),
    val typeOrder: TypeOrder = TypeOrder.DATE_ADDED,
    val sortOrder: SortOrder = SortOrder.ASCENDING
)