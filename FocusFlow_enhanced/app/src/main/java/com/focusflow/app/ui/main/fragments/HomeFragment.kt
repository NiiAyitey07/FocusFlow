package com.focusflow.app.ui.main.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.focusflow.app.databinding.FragmentHomeBinding
import com.focusflow.app.ui.main.adapters.TaskAdapter
import com.focusflow.app.ui.main.viewmodel.HomeViewModel
import com.focusflow.app.utils.DateUtils
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Date

/**
 * HomeFragment - placeholder home screen.
 * Shows the user's current goal and today's focus (UI can be expanded later).
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val vm: HomeViewModel by viewModels()

    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TaskAdapter(
            onToggle = { task, checked -> vm.toggleTask(task, checked) },
            onDelete = { task -> vm.deleteTask(task) },
            onEdit = { task -> showEditTaskDialog(task) }
        )

        binding.tasksList.layoutManager = LinearLayoutManager(requireContext())
        binding.tasksList.adapter = adapter

        // Observers
        vm.activeGoal.observe(viewLifecycleOwner) { goal ->
            if (goal == null) {
                binding.goalTitle.text = "No active goal"
                binding.goalMeta.text = "Tap More → Start a new goal"
                binding.goalProgress.progress = 0
                binding.streakText.text = "Streak: 0"
                binding.fabAdd.isEnabled = false
                return@observe
            }

            binding.fabAdd.isEnabled = true
            binding.goalTitle.text = goal.title

            // Bring forward unfinished tasks from earlier days (up to 3 total today)
            vm.ensureCarryover(goal.id)

            val today = DateUtils.startOfDay(Date())
            val elapsed = DateUtils.daysBetweenInclusive(goal.startDate, today)
            val total = DateUtils.daysBetweenInclusive(goal.startDate, goal.endDate)
            binding.goalProgress.max = total
            binding.goalProgress.progress = elapsed.coerceIn(0, total)
            binding.goalMeta.text = "Day ${elapsed.coerceIn(1, total)} of $total"
            binding.streakText.text = "Streak: ${goal.currentStreak}"
        }

        vm.todayTasks.observe(viewLifecycleOwner) { tasks ->
            adapter.submitList(tasks)
            val empty = tasks.isNullOrEmpty()
            binding.emptyState.visibility = if (empty) View.VISIBLE else View.GONE
            binding.emptyIcon.visibility = if (empty) View.VISIBLE else View.GONE
            binding.tasksList.visibility = if (empty) View.GONE else View.VISIBLE
        }

        vm.snackbar.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrBlank()) {
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
                vm.clearSnackbar()
            }
        }

        binding.fabAdd.setOnClickListener {
            val goal = vm.activeGoal.value
            if (goal?.id == null) {
                Snackbar.make(binding.root, "Set a goal first", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showAddTaskDialog(goal.id)
        }

        // Tiny polish: fade in
        binding.root.alpha = 0f
        binding.root.animate().alpha(1f).setDuration(220).start()
    }

    private fun showAddTaskDialog(goalId: Long) {
        val ctx = requireContext()
        val titleInput = android.widget.EditText(ctx).apply {
            hint = "Task title"
        }
        val descInput = android.widget.EditText(ctx).apply {
            hint = "Optional notes"
        }
        val container = android.widget.LinearLayout(ctx).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(48, 24, 48, 0)
            addView(titleInput)
            addView(descInput)
        }

        MaterialAlertDialogBuilder(ctx)
            .setTitle("Add today’s task")
            .setView(container)
            .setPositiveButton("Add") { _, _ ->
                vm.addTask(goalId, titleInput.text.toString(), descInput.text.toString())
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditTaskDialog(task: com.focusflow.app.data.model.Task) {
        val ctx = requireContext()
        val titleInput = android.widget.EditText(ctx).apply {
            setText(task.title)
            hint = "Task title"
        }
        val descInput = android.widget.EditText(ctx).apply {
            setText(task.description ?: "")
            hint = "Optional notes"
        }
        val container = android.widget.LinearLayout(ctx).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(48, 24, 48, 0)
            addView(titleInput)
            addView(descInput)
        }

        MaterialAlertDialogBuilder(ctx)
            .setTitle("Edit task")
            .setView(container)
            .setPositiveButton("Save") { _, _ ->
                vm.editTask(task, titleInput.text.toString(), descInput.text.toString())
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
