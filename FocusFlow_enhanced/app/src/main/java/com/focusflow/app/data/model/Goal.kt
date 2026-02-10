package com.focusflow.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Goal Entity - represents a 30-day goal
 */
@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val duration: Duration,
    val startDate: Date,
    val endDate: Date,
    val isActive: Boolean = true,
    val completedDays: Int = 0,
    val currentStreak: Int = 0,
    val createdAt: Date = Date()
)

/**
 * Goal Duration options
 */
enum class Duration {
    DAILY,    // Daily commitment
    WEEKLY,   // Weekly goals
    MONTHLY   // Monthly goals
}
