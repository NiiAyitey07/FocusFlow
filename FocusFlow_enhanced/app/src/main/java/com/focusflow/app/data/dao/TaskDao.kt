package com.focusflow.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.focusflow.app.data.model.Task
import java.util.Date

/**
 * Data Access Object for Tasks
 */
@Dao
interface TaskDao {
    
    @Query("SELECT * FROM tasks WHERE goalId = :goalId AND date = :date ORDER BY createdAt ASC")
    fun getTasksForDate(goalId: Long, date: Date): LiveData<List<Task>>
    
    @Query("SELECT * FROM tasks WHERE goalId = :goalId ORDER BY date DESC")
    fun getAllTasksForGoal(goalId: Long): LiveData<List<Task>>
    
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): Task?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long
    
    @Update
    suspend fun updateTask(task: Task)
    
    @Delete
    suspend fun deleteTask(task: Task)
    
    @Query("UPDATE tasks SET isCompleted = :completed, completedAt = :completedAt WHERE id = :taskId")
    suspend fun markTaskComplete(taskId: Long, completed: Boolean, completedAt: Date?)
    
    @Query("SELECT COUNT(*) FROM tasks WHERE goalId = :goalId AND date = :date")
    suspend fun getTaskCountForDate(goalId: Long, date: Date): Int
    
    @Query("SELECT COUNT(*) FROM tasks WHERE goalId = :goalId AND isCompleted = 1")
    suspend fun getCompletedTaskCount(goalId: Long): Int

    // --- Aggregates for progress/streak ---
    @Query("SELECT DISTINCT date FROM tasks WHERE goalId = :goalId ORDER BY date DESC")
    suspend fun getDistinctTaskDates(goalId: Long): List<Date>

    @Query("SELECT COUNT(*) FROM tasks WHERE goalId = :goalId AND date = :date")
    suspend fun getTotalTaskCountForDate(goalId: Long, date: Date): Int

    @Query("SELECT COUNT(*) FROM tasks WHERE goalId = :goalId AND date = :date AND isCompleted = 0")
    suspend fun getIncompleteTaskCountForDate(goalId: Long, date: Date): Int

    // --- Carryover + edits ---
    @Query("SELECT * FROM tasks WHERE goalId = :goalId AND date < :today AND isCompleted = 0 ORDER BY date ASC, createdAt ASC")
    suspend fun getIncompleteTasksBefore(goalId: Long, today: Date): List<Task>

    @Query("UPDATE tasks SET date = :newDate WHERE id = :taskId")
    suspend fun updateTaskDate(taskId: Long, newDate: Date)

    @Query("UPDATE tasks SET title = :title, description = :description WHERE id = :taskId")
    suspend fun updateTaskDetails(taskId: Long, title: String, description: String?)

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()
}
