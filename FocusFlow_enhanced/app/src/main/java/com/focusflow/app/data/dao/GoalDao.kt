package com.focusflow.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.focusflow.app.data.model.Goal

/**
 * Data Access Object for Goals
 */
@Dao
interface GoalDao {
    
    @Query("SELECT * FROM goals WHERE isActive = 1 LIMIT 1")
    fun getActiveGoal(): LiveData<Goal?>
    
    @Query("SELECT * FROM goals ORDER BY createdAt DESC")
    fun getAllGoals(): LiveData<List<Goal>>
    
    @Query("SELECT * FROM goals WHERE id = :goalId")
    suspend fun getGoalById(goalId: Long): Goal?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal): Long
    
    @Update
    suspend fun updateGoal(goal: Goal)
    
    @Delete
    suspend fun deleteGoal(goal: Goal)
    
    @Query("UPDATE goals SET isActive = 0 WHERE id = :goalId")
    suspend fun deactivateGoal(goalId: Long)

    @Query("UPDATE goals SET isActive = 0")
    suspend fun deactivateAllGoals()
    
    @Query("UPDATE goals SET completedDays = :days, currentStreak = :streak WHERE id = :goalId")
    suspend fun updateProgress(goalId: Long, days: Int, streak: Int)

    @Query("DELETE FROM goals")
    suspend fun deleteAll()
}
