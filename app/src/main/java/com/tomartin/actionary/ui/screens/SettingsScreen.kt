package com.tomartin.actionary.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.tomartin.actionary.R
import com.tomartin.actionary.ui.components.SettingItem
import com.tomartin.actionary.ui.viewmodels.SettingsViewModel
import com.tomartin.actionary.utils.ThemeChooser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navHostController: NavHostController,
    viewModel: SettingsViewModel
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.settingsUIState.collectAsStateWithLifecycle()

    var isOptionSelected by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        viewModel.getDeleteTasksWhenCompleted()
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, null)
                    }
                },
                scrollBehavior = topAppBarScrollBehavior,
                modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
            )
        },
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(28.dp),
            modifier = Modifier
                .fillMaxWidth()
                .scrollable(rememberScrollState(), Orientation.Vertical)
                .padding(start = 20.dp, end = 20.dp, top = paddingValues.calculateTopPadding())
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = stringResource(R.string.appearance),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                SettingItem(
                    headLine = stringResource(R.string.theme),
                    supportiveContent = when (uiState.uiTheme) {
                        com.tomartin.actionary.utils.settings.ApplicationTheme.LIGHT -> stringResource(R.string.light)
                        com.tomartin.actionary.utils.settings.ApplicationTheme.DARK -> stringResource(R.string.dark)
                        com.tomartin.actionary.utils.settings.ApplicationTheme.SYSTEM -> stringResource(R.string.system)
                    },
                    modifier = Modifier.fillMaxWidth().clickable { navHostController.navigate(ThemeChooser) }
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = stringResource(R.string.behavior),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SettingItem(
                        headLine = stringResource(R.string.delete_completed_tasks),
                        supportiveContent = stringResource(R.string.delete_tasks_when_completed),
                    )
                    Switch(
                        checked = uiState.deleteTasksWhenCompleted,
                        onCheckedChange = {
                            isOptionSelected = !isOptionSelected
                            viewModel.setDeleteTasksWhenCompleted(isOptionSelected)
                        }
                    )
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = stringResource(R.string.about),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                SettingItem(
                    headLine = stringResource(R.string.application_name),
                    supportiveContent = stringResource(R.string.actionary),
                )
                SettingItem(
                    headLine = stringResource(R.string.version),
                    supportiveContent = LocalContext.current.packageManager.getPackageInfo(LocalContext.current.packageName, 0).versionName!!,
                )
            }
        }
    }
}