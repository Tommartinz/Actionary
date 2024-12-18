package com.tomartin.actionary.ui.components.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.tomartin.actionary.ui.viewmodels.TasksViewModel
import com.tomartin.actionary.R
import com.tomartin.actionary.data.database.Task

@Composable
fun TaskEditorDialog(
    taskId: Int,
    onDismiss: () -> Unit,
    viewModel: TasksViewModel
) {
    var taskName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getTaskById(taskId).collect {
            taskName = it.name
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(Icons.Rounded.TaskAlt, null) },
        title = { Text(stringResource(R.string.edit_task)) },
        text = {
            OutlinedTextField(
                value = taskName,
                onValueChange = { taskName = it },
                label = { Text(stringResource(R.string.name)) },
                maxLines = 1
            )
        },
        dismissButton = {
            TextButton(onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.updateTask(Task(taskId, taskName))
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.edit))
            }
        },
    )
}