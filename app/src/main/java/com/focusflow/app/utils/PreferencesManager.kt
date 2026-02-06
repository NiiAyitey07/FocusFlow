package com.focusflow.app.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Simple SharedPreferences Manager
 */
class PreferencesManager(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "focusflow_prefs",
        Context.MODE_PRIVATE
    )
    
    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_CURRENT_GOAL_ID = "current_goal_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_STREAK_COUNT = "streak_count"
        private const val KEY_LAST_COMPLETION_DATE = "last_completion_date"
    }
    
    fun isFirstLaunch(): Boolean {
        return prefs.getBoolean(KEY_FIRST_LAUNCH, true)
    }
    
    fun setFirstLaunchComplete() {
        prefs.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
    }
    
    fun setCurrentGoalId(goalId: Long) {
        prefs.edit().putLong(KEY_CURRENT_GOAL_ID, goalId).apply()
    }
    
    fun getCurrentGoalId(): Long {
        return prefs.getLong(KEY_CURRENT_GOAL_ID, -1L)
    }
    
    fun setUserName(name: String) {
        prefs.edit().putString(KEY_USER_NAME, name).apply()
    }
    
    fun getUserName(): String? {
        return prefs.getString(KEY_USER_NAME, null)
    }
    
    fun setStreakCount(count: Int) {
        prefs.edit().putInt(KEY_STREAK_COUNT, count).apply()
    }
    
    fun getStreakCount(): Int {
        return prefs.getInt(KEY_STREAK_COUNT, 0)
    }
    
    fun setLastCompletionDate(date: String) {
        prefs.edit().putString(KEY_LAST_COMPLETION_DATE, date).apply()
    }
    
    fun getLastCompletionDate(): String? {
        return prefs.getString(KEY_LAST_COMPLETION_DATE, null)
    }
    
    fun clearAll() {
        prefs.edit().clear().apply()
    }
}
