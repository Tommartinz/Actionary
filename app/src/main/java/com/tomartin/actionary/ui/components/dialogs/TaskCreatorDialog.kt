package com.tomartin.actionary.ui.components.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.tomartin.actionary.ui.viewmodels.TasksViewModel
import com.tomartin.actionary.R
import com.tomartin.actionary.data.database.Task

@Composable
fun TaskCreatorDialog(
    onDismiss: () -> Unit,
    viewModel: TasksViewModel
) {
    var name by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(Icons.Rounded.TaskAlt, null) },
        title = { Text(stringResource(R.string.new_task)) },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
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
                    viewModel.createTask(Task(name = name))
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.create))
            }
        },
    )
}