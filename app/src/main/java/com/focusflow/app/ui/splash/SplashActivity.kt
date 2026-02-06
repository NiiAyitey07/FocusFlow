package com.focusflow.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.focusflow.app.R
import com.focusflow.app.ui.onboarding.OnboardingActivity
import com.focusflow.app.ui.main.MainActivity
import com.focusflow.app.utils.PreferencesManager

/**
 * Splash Screen - shows FocusFlow logo on launch
 */
class SplashActivity : AppCompatActivity() {
    
    private val SPLASH_DELAY = 2000L // 2 seconds
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        // Check if first launch
        Handler(Looper.getMainLooper()).postDelayed({
            val prefsManager = PreferencesManager(this)
            
            val intent = if (prefsManager.isFirstLaunch()) {
                Intent(this, OnboardingActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java)
            }
            
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }
}
