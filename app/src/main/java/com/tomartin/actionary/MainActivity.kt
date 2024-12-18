package com.tomartin.actionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.tomartin.actionary.ui.components.dialogs.TaskCreatorDialog
import com.tomartin.actionary.ui.components.dialogs.TaskEditorDialog
import com.tomartin.actionary.ui.components.dialogs.ThemeChooser
import com.tomartin.actionary.ui.screens.ListScreen
import com.tomartin.actionary.ui.screens.SettingsScreen
import com.tomartin.actionary.ui.theme.AlmanacTheme
import com.tomartin.actionary.ui.viewmodels.SettingsViewModel
import com.tomartin.actionary.ui.viewmodels.TasksViewModel
import com.tomartin.actionary.utils.EditTask
import com.tomartin.actionary.utils.ListTask
import com.tomartin.actionary.utils.Preferences
import com.tomartin.actionary.utils.Settings
import com.tomartin.actionary.utils.TaskCreator
import com.tomartin.actionary.utils.ThemeChooser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val tasksViewModel = hiltViewModel<TasksViewModel>()
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            AlmanacTheme(viewModel = settingsViewModel) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ListTask
                ) {
                    composable<ListTask> {
                        ListScreen(
                            navHostController = navController,
                            tasksViewModel = tasksViewModel,
                            settingsViewModel = settingsViewModel
                        )
                    }
                    dialog<TaskCreator> {
                        TaskCreatorDialog(
                            onDismiss = { navController.popBackStack() },
                            viewModel = tasksViewModel
                        )
                    }
                    dialog<EditTask> { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getInt("taskId")

                        TaskEditorDialog(
                            taskId = taskId!!,
                            onDismiss = { navController.popBackStack() },
                            viewModel = tasksViewModel,
                        )
                    }
                    navigation<Settings>(startDestination = Preferences) {
                        composable<Preferences>(
                            enterTransition = {
                                fadeIn(
                                    animationSpec = tween(
                                        300, easing = LinearEasing
                                    )
                                ) + slideIntoContainer(
                                    animationSpec = tween(300, easing = EaseIn),
                                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                                )
                            },
                            exitTransition = {
                                fadeOut(
                                    animationSpec = tween(
                                        300, easing = LinearEasing
                                    )
                                ) + slideOutOfContainer(
                                    animationSpec = tween(300, easing = EaseOut),
                                    towards = AnimatedContentTransitionScope.SlideDirection.End
                                )
                            }
                        ) {
                            SettingsScreen(
                                navHostController = navController,
                                viewModel = settingsViewModel
                            )
                        }
                        dialog<ThemeChooser> {
                            ThemeChooser(
                                viewModel = settingsViewModel,
                                onDismiss = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}