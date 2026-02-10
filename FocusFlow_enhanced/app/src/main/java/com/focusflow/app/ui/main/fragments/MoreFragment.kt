package com.focusflow.app.ui.main.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.focusflow.app.data.repository.GoalRepository
import com.focusflow.app.data.repository.TaskRepository
import com.focusflow.app.databinding.FragmentMoreBinding
import com.focusflow.app.notifications.NotificationScheduler
import com.focusflow.app.ui.goal.GoalSetupActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * MoreFragment - placeholder settings/privacy screen.
 */
class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    private val goalRepo = GoalRepository()
    private val taskRepo = TaskRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNewGoal.setOnClickListener {
            startActivity(Intent(requireContext(), GoalSetupActivity::class.java))
        }

        binding.btnClearAll.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Reset all data?")
                .setMessage("This will delete your goal and all tasks on this device.")
                .setPositiveButton("Reset") { _, _ ->
                    val appCtx = requireContext().applicationContext
                    lifecycleScope.launch(Dispatchers.IO) {
                        taskRepo.deleteAll()
                        goalRepo.deleteAll()
                        NotificationScheduler.cancelReminders(appCtx)
                    }
                    Snackbar.make(binding.root, "Data reset", Snackbar.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // tiny polish
        binding.root.alpha = 0f
        binding.root.animate().alpha(1f).setDuration(200).start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
