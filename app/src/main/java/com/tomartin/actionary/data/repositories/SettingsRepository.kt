package com.tomartin.actionary.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tomartin.actionary.utils.settings.ApplicationTheme
import com.tomartin.actionary.utils.settings.SortOrder
import com.tomartin.actionary.utils.settings.TypeOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Suppress("PrivatePropertyName")
class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val TYPE_ORDER = stringPreferencesKey("type_order")
    private val SORT_ORDER = stringPreferencesKey("sort_order")
    private val DELETE_TASKS_WHEN_COMPLETED = booleanPreferencesKey("delete_tasks_when_completed")
    private val APPLICATION_THEME = stringPreferencesKey("application_theme")

    fun getTypeOrder(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TYPE_ORDER] ?: TypeOrder.DATE_ADDED.name
        }
    }

    suspend fun setTypeOrder(typeOrder: TypeOrder) {
        dataStore.edit { setting ->
            setting[TYPE_ORDER] = typeOrder.name
        }
    }

    fun getSortOrder(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[SORT_ORDER] ?: SortOrder.ASCENDING.name
        }
    }

    suspend fun setSortOrder(sortOrder: SortOrder) {
        dataStore.edit { setting ->
            setting[SORT_ORDER] = sortOrder.name
        }
    }

    fun getDeleteTasksWhenCompleted(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[DELETE_TASKS_WHEN_COMPLETED] ?: false
        }
    }

    suspend fun setDeleteTasksWhenCompleted(deleteTasksWhenCompleted: Boolean) {
        dataStore.edit { setting ->
            setting[DELETE_TASKS_WHEN_COMPLETED] = deleteTasksWhenCompleted
        }
    }

    fun getApplicationTheme(): Flow<String> {
        return dataStore.data.map { theme ->
            theme[APPLICATION_THEME] ?: ApplicationTheme.SYSTEM.name
        }
    }

    suspend fun setApplicationTheme(applicationTheme: ApplicationTheme) {
        dataStore.edit { theme ->
            theme[APPLICATION_THEME] = applicationTheme.name
        }
    }
}