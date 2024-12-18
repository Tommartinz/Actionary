package com.tomartin.actionary.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskController {
    @Insert
    suspend fun insertTask(task: Task)

    @Query("SELECT * FROM task ORDER BY CASE WHEN :isAscending = 1 THEN id END ASC, CASE WHEN :isAscending = 0 THEN id END DESC")
    fun getAllTasksByDateAdded(isAscending: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task ORDER BY CASE WHEN :isAscending = 1 THEN name END ASC, CASE WHEN :isAscending = 0 THEN name END DESC")
    fun queryAllTasksByName(isAscending: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id = :id")
    fun queryTaskById(id: Int): Flow<Task>

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}