package com.tomartin.actionary.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomartin.actionary.ui.viewmodels.SettingsViewModel
import com.tomartin.actionary.utils.settings.ApplicationTheme

@Composable
fun AlmanacTheme(
    isSystemDarkTheme: Boolean = isSystemInDarkTheme(),
    viewModel: SettingsViewModel,
    content: @Composable () -> Unit
) {
    val themeState by viewModel.settingsUIState.collectAsStateWithLifecycle()

    LaunchedEffect(themeState.uiTheme) {
        viewModel.getApplicationTheme()
    }

    val colorScheme = when (themeState.uiTheme) {
        ApplicationTheme.SYSTEM -> if (isSystemDarkTheme) DarkScheme else LightScheme
        ApplicationTheme.DARK -> DarkScheme
        ApplicationTheme.LIGHT -> LightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}