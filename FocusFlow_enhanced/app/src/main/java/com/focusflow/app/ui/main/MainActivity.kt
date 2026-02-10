package com.focusflow.app.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.focusflow.app.R
import com.focusflow.app.notifications.NotificationScheduler
import com.focusflow.app.ui.main.fragments.HomeFragment
import com.focusflow.app.ui.main.fragments.MoreFragment
import com.focusflow.app.ui.main.fragments.ProgressFragment
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

        // Notifications: request permission (Android 13+) and schedule daily reminders.
        maybeEnableReminders()

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment()); true
                }
                R.id.nav_progress -> {
                    loadFragment(ProgressFragment()); true
                }
                R.id.nav_more -> {
                    loadFragment(MoreFragment()); true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.nav_home
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun maybeEnableReminders() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            if (!granted) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
                return
            }
        }
        NotificationScheduler.scheduleDailyReminders(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            val granted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (granted) {
                NotificationScheduler.scheduleDailyReminders(this)
            }
        }
    }
}
