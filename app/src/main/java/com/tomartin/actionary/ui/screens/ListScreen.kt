package com.tomartin.actionary.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tomartin.actionary.R
import com.tomartin.actionary.data.database.Task
import com.tomartin.actionary.ui.components.SortSheet
import com.tomartin.actionary.ui.components.TaskItem
import com.tomartin.actionary.ui.viewmodels.SettingsViewModel
import com.tomartin.actionary.ui.viewmodels.TasksViewModel
import com.tomartin.actionary.utils.EditTask
import com.tomartin.actionary.utils.Settings
import com.tomartin.actionary.utils.TaskCreator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navHostController: NavHostController,
    tasksViewModel: TasksViewModel,
    settingsViewModel: SettingsViewModel
) {
    val tasksUIState by tasksViewModel.tasksUiState.collectAsStateWithLifecycle()
    val settingsUIState by settingsViewModel.settingsUIState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val sheetState = rememberModalBottomSheetState(false)
    var isSheetOpen by remember { mutableStateOf(false) }

    LaunchedEffect(navHostController.currentBackStackEntryAsState().value?.destination?.route, tasksUIState) {
        tasksViewModel.getAllTasks()
    }

    val density = LocalDensity.current

    fun isVisible(item: Task): Boolean {
        return tasksUIState.tasks.contains(item)
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(R.string.actionary)) },
                actions = {
                    IconButton(onClick = { isSheetOpen = true }) {
                        Icon(Icons.AutoMirrored.Rounded.Sort, null)
                    }
                    IconButton(onClick = { navHostController.navigate(Settings) }) {
                        Icon(Icons.Rounded.Settings, null)
                    }
                },
                scrollBehavior = topAppBarScrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navHostController.navigate(TaskCreator) }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                )
            }
        },
        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        if (isSheetOpen) {
            SortSheet(
                sheetState = sheetState,
                onDismiss = { coroutineScope.launch { isSheetOpen = false } },
                viewModel = tasksViewModel
            )
        }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            items(count = tasksUIState.tasks.size, key = { key -> tasksUIState.tasks[key].id }) { item ->
                AnimatedVisibility(
                    visible = isVisible(tasksUIState.tasks[item]),
                    enter = slideInVertically {
                        with(density) { -40.dp.roundToPx() }
                    } + fadeIn(initialAlpha = 0.3f),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    TaskItem(
                        task = tasksUIState.tasks[item],
                        onCheckedChange = {
                            tasksViewModel.updateTask(tasksUIState.tasks[item].copy(isCompleted = it))
                            if (settingsUIState.deleteTasksWhenCompleted) {
                                tasksViewModel.deleteTask(tasksUIState.tasks[item])
                            }
                        },
                        onDelete = { tasksViewModel.deleteTask(tasksUIState.tasks[item]) },
                        onEdit = { navHostController.navigate(EditTask(tasksUIState.tasks[item].id)) }
                    )
                }
                if (item < tasksUIState.tasks.lastIndex) {
                    HorizontalDivider(
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}