package com.focusflow.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.focusflow.app.data.dao.GoalDao
import com.focusflow.app.data.dao.TaskDao
import com.focusflow.app.data.model.Goal
import com.focusflow.app.data.model.Task
import com.focusflow.app.data.model.Converters

/**
 * Room Database for FocusFlow
 * Local storage - no cloud sync needed
 */
@Database(
    entities = [Goal::class, Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FocusFlowDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun taskDao(): TaskDao
}
