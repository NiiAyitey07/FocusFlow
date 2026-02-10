package com.focusflow.app.data.repository

import androidx.lifecycle.LiveData
import com.focusflow.app.FocusFlowApplication
import com.focusflow.app.data.model.Goal

class GoalRepository {
    private val goalDao = FocusFlowApplication.database.goalDao()

    fun getActiveGoal(): LiveData<Goal?> = goalDao.getActiveGoal()

    suspend fun deactivateAll() {
        goalDao.deactivateAllGoals()
    }

    suspend fun insert(goal: Goal): Long = goalDao.insertGoal(goal)

    suspend fun updateProgress(goalId: Long, days: Int, streak: Int) {
        goalDao.updateProgress(goalId, days, streak)
    }

    suspend fun deleteAll() {
        goalDao.deleteAll()
    }
}
