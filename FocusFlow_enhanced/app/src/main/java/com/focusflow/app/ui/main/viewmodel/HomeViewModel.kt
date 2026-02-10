package com.focusflow.app.ui.main.viewmodel

import androidx.lifecycle.*
import com.focusflow.app.data.model.Goal
import com.focusflow.app.data.model.Task
import com.focusflow.app.data.repository.GoalRepository
import com.focusflow.app.data.repository.TaskRepository
import com.focusflow.app.utils.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel(
    private val goalRepo: GoalRepository = GoalRepository(),
    private val taskRepo: TaskRepository = TaskRepository()
) : ViewModel() {

    val activeGoal: LiveData<Goal?> = goalRepo.getActiveGoal()

    private val today: Date = DateUtils.startOfDay(Date())

    val todayTasks: LiveData<List<Task>> = activeGoal.switchMap { goal ->
        if (goal?.id != null) taskRepo.getTasksForDate(goal.id, today) else MutableLiveData(emptyList())
    }

    private val _snackbar = MutableLiveData<String?>()
    val snackbar: LiveData<String?> = _snackbar

    fun clearSnackbar() {
        _snackbar.value = null
    }

    fun addTask(goalId: Long, title: String, description: String?) {
        val t = title.trim()
        if (t.isEmpty()) {
            _snackbar.value = "Task title can’t be empty"
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val count = taskRepo.getTaskCountForDate(goalId, today)
            if (count >= 3) {
                _snackbar.postValue("Keep it focused: max 3 tasks per day")
                return@launch
            }

            taskRepo.insert(
                Task(
                    goalId = goalId,
                    title = t,
                    description = description?.trim()?.takeIf { it.isNotEmpty() },
                    date = today
                )
            )

            // Adding a new (incomplete) task can affect whether today counts as a completed day,
            // so we recompute progress as well.
            recomputeProgress(goalId)
        }
    }

    fun editTask(task: Task, newTitle: String, newDescription: String?) {
        val t = newTitle.trim()
        if (t.isEmpty()) {
            _snackbar.value = "Task title can’t be empty"
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            taskRepo.updateDetails(task.id, t, newDescription?.trim()?.takeIf { it.isNotEmpty() })
            recomputeProgress(task.goalId)
        }
    }

    /**
     * Carry over unfinished tasks from earlier days into today (up to 3 total today).
     */
    fun ensureCarryover(goalId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val moved = taskRepo.carryOverUnfinished(goalId, today, maxPerDay = 3)
            if (moved > 0) {
                _snackbar.postValue("Carried over $moved unfinished task${if (moved == 1) "" else "s"} into today")
                recomputeProgress(goalId)
            }
        }
    }

    fun toggleTask(task: Task, completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val now = if (completed) Date() else null
            taskRepo.toggleComplete(task, completed, now)
            // Recompute goal progress on any toggle
            recomputeProgress(task.goalId)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepo.delete(task)
            recomputeProgress(task.goalId)
        }
    }

    private suspend fun recomputeProgress(goalId: Long) {
        // A day is "completed" if it had at least one task and ALL tasks were completed.
        // Streak is consecutive completed days ending today.
        val dates = taskRepo.getDistinctTaskDates(goalId)
        var completedDays = 0
        val completedByDate = mutableSetOf<Long>()

        for (rawDate in dates) {
            val d = DateUtils.startOfDay(rawDate)
            val total = taskRepo.getTotalTaskCountForDate(goalId, d)
            if (total <= 0) continue
            val incomplete = taskRepo.getIncompleteTaskCountForDate(goalId, d)
            if (incomplete == 0) {
                completedDays += 1
                completedByDate.add(DateUtils.startOfDay(d).time)
            }
        }

        // Streak is consecutive completed days going backwards from today.
        var streak = 0
        var cursor = today.time
        val oneDay = 24L * 60L * 60L * 1000L
        while (completedByDate.contains(cursor)) {
            streak += 1
            cursor -= oneDay
        }

        goalRepo.updateProgress(goalId, completedDays, streak)
    }
}
