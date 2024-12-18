package com.tomartin.actionary.utils

import kotlinx.serialization.Serializable

@Serializable
object ListTask

@Serializable
object TaskCreator

@Serializable
data class EditTask(val taskId: Int)

@Serializable
object Settings

@Serializable
object Preferences

@Serializable
object ThemeChooser