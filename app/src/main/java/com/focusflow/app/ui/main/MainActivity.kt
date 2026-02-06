package com.focusflow.app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.focusflow.app.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * MainActivity - Main app container with bottom navigation
 * Hosts Home, Progress, and More fragments
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var bottomNav: BottomNavigationView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize bottom navigation
        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Load Home Fragment
                    true
                }
                R.id.nav_progress -> {
                    // Load Progress Fragment
                    true
                }
                R.id.nav_more -> {
                    // Load More Fragment
                    true
                }
                else -> false
            }
        }
        
        // Load default fragment (Home)
        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.nav_home
        }
    }
    
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
