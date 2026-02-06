package com.focusflow.app.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.focusflow.app.R
import com.focusflow.app.ui.goal.GoalSetupActivity
import com.focusflow.app.utils.PreferencesManager

/**
 * OnboardingActivity - 2-page intro (Welcome + Privacy)
 */
class OnboardingActivity : AppCompatActivity() {
    
    private lateinit var prefsManager: PreferencesManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        
        prefsManager = PreferencesManager(this)
        
        // TODO: Implement ViewPager2 with 2 pages
        // Page 1: Welcome
        // Page 2: Privacy First
        
        // For now, just mark onboarding complete and go to goal setup
        findViewById<android.widget.Button>(R.id.btn_get_started)?.setOnClickListener {
            prefsManager.setFirstLaunchComplete()
            startActivity(Intent(this, GoalSetupActivity::class.java))
            finish()
        }
    }
}
