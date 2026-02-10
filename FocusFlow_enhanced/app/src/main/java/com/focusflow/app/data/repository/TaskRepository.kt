package com.focusflow.app.data.repository

import androidx.lifecycle.LiveData
import com.focusflow.app.FocusFlowApplication
import com.focusflow.app.data.model.Task
import java.util.Date

class TaskRepository {
    private val taskDao = FocusFlowApplication.database.taskDao()

    fun getTasksForDate(goalId: Long, date: Date): LiveData<List<Task>> =
        taskDao.getTasksForDate(goalId, date)

    suspend fun insert(task: Task): Long = taskDao.insertTask(task)

    suspend fun toggleComplete(task: Task, completed: Boolean, completedAt: Date?) {
        taskDao.markTaskComplete(task.id, completed, completedAt)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun updateDetails(taskId: Long, title: String, description: String?) {
        taskDao.updateTaskDetails(taskId, title, description)
    }

    /**
     * Carry over unfinished tasks from previous days into [today].
     * We update the existing task's date (keeps history light, avoids migrations).
     */
    suspend fun carryOverUnfinished(goalId: Long, today: Date, maxPerDay: Int = 3): Int {
        val todaysCount = taskDao.getTaskCountForDate(goalId, today)
        val roomLeft = (maxPerDay - todaysCount).coerceAtLeast(0)
        if (roomLeft == 0) return 0

        val candidates = taskDao.getIncompleteTasksBefore(goalId, today)
        if (candidates.isEmpty()) return 0

        val toMove = candidates.take(roomLeft)
        toMove.forEach { t ->
            taskDao.updateTaskDate(t.id, today)
        }
        return toMove.size
    }

    suspend fun getTaskCountForDate(goalId: Long, date: Date): Int = taskDao.getTaskCountForDate(goalId, date)

    suspend fun getDistinctTaskDates(goalId: Long): List<Date> = taskDao.getDistinctTaskDates(goalId)

    suspend fun getTotalTaskCountForDate(goalId: Long, date: Date): Int = taskDao.getTotalTaskCountForDate(goalId, date)

    suspend fun getIncompleteTaskCountForDate(goalId: Long, date: Date): Int = taskDao.getIncompleteTaskCountForDate(goalId, date)

    suspend fun deleteAll() {
        taskDao.deleteAll()
    }
}
