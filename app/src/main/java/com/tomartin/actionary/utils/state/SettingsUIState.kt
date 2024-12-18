package com.tomartin.actionary.utils.state

import com.tomartin.actionary.utils.settings.ApplicationTheme

data class SettingsUIState(
    val uiTheme: ApplicationTheme = ApplicationTheme.SYSTEM,
    val deleteTasksWhenCompleted: Boolean = false
)