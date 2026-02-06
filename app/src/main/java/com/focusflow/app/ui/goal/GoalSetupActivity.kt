package com.focusflow.app.ui.goal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.focusflow.app.FocusFlowApplication
import com.focusflow.app.R
import com.focusflow.app.data.model.Duration
import com.focusflow.app.data.model.Goal
import com.focusflow.app.ui.main.MainActivity
import com.focusflow.app.utils.PreferencesManager
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch
import java.util.*

/**
 * GoalSetupActivity - Create 30-day goal
 */
class GoalSetupActivity : AppCompatActivity() {
    
    private lateinit var prefsManager: PreferencesManager
    private var selectedDuration = Duration.DAILY
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_setup)
        
        prefsManager = PreferencesManager(this)
        
        val durationChips = findViewById<ChipGroup>(R.id.duration_chips)
        val goalInput = findViewById<EditText>(R.id.goal_input)
        val whyInput = findViewById<EditText>(R.id.why_input)
        val commitButton = findViewById<Button>(R.id.btn_commit)
        
        // Duration selection
        durationChips.setOnCheckedChangeListener { _, checkedId ->
            selectedDuration = when (checkedId) {
                R.id.chip_daily -> Duration.DAILY
                R.id.chip_weekly -> Duration.WEEKLY
                R.id.chip_monthly -> Duration.MONTHLY
                else -> Duration.DAILY
            }
        }
        
        // Commit goal
        commitButton.setOnClickListener {
            val goalTitle = goalInput.text.toString().trim()
            val goalWhy = whyInput.text.toString().trim()
            
            if (goalTitle.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_goal, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // Create goal
            val startDate = Date()
            val calendar = Calendar.getInstance()
            calendar.time = startDate
            calendar.add(Calendar.DAY_OF_YEAR, 30)
            val endDate = calendar.time
            
            val goal = Goal(
                title = goalTitle,
                description = goalWhy.ifEmpty { null },
                duration = selectedDuration,
                startDate = startDate,
                endDate = endDate
            )
            
            // Save to database
            lifecycleScope.launch {
                val goalId = FocusFlowApplication.database.goalDao().insertGoal(goal)
                prefsManager.setCurrentGoalId(goalId)
                
                // Navigate to main app
                startActivity(Intent(this@GoalSetupActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}
