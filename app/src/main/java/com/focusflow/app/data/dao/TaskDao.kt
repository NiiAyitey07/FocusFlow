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
}
