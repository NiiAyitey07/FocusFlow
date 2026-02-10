package com.focusflow.app

import android.app.Application
import androidx.room.Room
import com.focusflow.app.data.database.FocusFlowDatabase

/**
 * FocusFlow Application Class
 * Initializes app-wide components
 */
class FocusFlowApplication : Application() {
    
    companion object {
        lateinit var database: FocusFlowDatabase
            private set
    }
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Room database
        database = Room.databaseBuilder(
            applicationContext,
            FocusFlowDatabase::class.java,
            "focusflow_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
