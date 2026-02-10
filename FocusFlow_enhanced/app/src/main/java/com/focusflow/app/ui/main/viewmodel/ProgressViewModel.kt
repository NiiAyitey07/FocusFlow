package com.focusflow.app.ui.main.viewmodel

import androidx.lifecycle.*
import com.focusflow.app.data.model.Goal
import com.focusflow.app.data.repository.GoalRepository
import com.focusflow.app.data.repository.TaskRepository
import com.focusflow.app.utils.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

data class ProgressSummary(
    val goalTitle: String,
    val completedDays: Int,
    val streak: Int,
    val daysElapsed: Int,
    val daysTotal: Int,
    val completionRate: Int // 0..100
)

class ProgressViewModel(
    private val goalRepo: GoalRepository = GoalRepository(),
    private val taskRepo: TaskRepository = TaskRepository()
) : ViewModel() {

    val activeGoal: LiveData<Goal?> = goalRepo.getActiveGoal()

    private val _summary = MutableLiveData<ProgressSummary?>()
    val summary: LiveData<ProgressSummary?> = _summary

    fun refresh() {
        val goal = activeGoal.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val today = DateUtils.startOfDay(Date())
            val daysElapsed = DateUtils.daysBetweenInclusive(goal.startDate, today).coerceAtLeast(1)
            val daysTotal = DateUtils.daysBetweenInclusive(goal.startDate, goal.endDate)
            val completionRate = ((goal.completedDays.toDouble() / daysElapsed.toDouble()) * 100.0)
                .toInt()
                .coerceIn(0, 100)

            _summary.postValue(
                ProgressSummary(
                    goalTitle = goal.title,
                    completedDays = goal.completedDays,
                    streak = goal.currentStreak,
                    daysElapsed = daysElapsed,
                    daysTotal = daysTotal,
                    completionRate = completionRate
                )
            )
        }
    }

    fun resetAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepo.deleteAll()
            goalRepo.deleteAll()
            _summary.postValue(null)
        }
    }
}
