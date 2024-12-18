package com.tomartin.actionary.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomartin.actionary.R
import com.tomartin.actionary.ui.viewmodels.SettingsViewModel
import com.tomartin.actionary.utils.settings.ApplicationTheme

@Composable
fun ThemeChooser(
    viewModel: SettingsViewModel,
    onDismiss: () -> Unit
) {
    val uiState by viewModel.settingsUIState.collectAsStateWithLifecycle()
    var theme by remember { mutableStateOf(uiState.uiTheme) }

    LaunchedEffect(uiState) {
        viewModel.getApplicationTheme()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(Icons.Rounded.LightMode, null) },
        title = { Text(stringResource(R.string.choose_theme)) },
        text = {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.light),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    RadioButton(
                        selected = theme == ApplicationTheme.LIGHT,
                        onClick = { theme = ApplicationTheme.LIGHT }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.dark),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    RadioButton(
                        selected = theme == ApplicationTheme.DARK,
                        onClick = { theme = ApplicationTheme.DARK }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    Text(
                        text = stringResource(R.string.system),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    RadioButton(
                        selected = theme == ApplicationTheme.SYSTEM,
                        onClick = { theme = ApplicationTheme.SYSTEM }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.setApplicationTheme(theme)
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.choose))
            }
        },
        dismissButton = {
            TextButton(onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}