package com.focusflow.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Task Entity - represents a daily task
 */
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val goalId: Long,
    val title: String,
    val description: String? = null,
    val date: Date,
    val isCompleted: Boolean = false,
    val completedAt: Date? = null,
    val createdAt: Date = Date()
)
