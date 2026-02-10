package com.focusflow.app.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.focusflow.app.databinding.FragmentProgressBinding
import com.focusflow.app.ui.main.viewmodel.ProgressViewModel

/**
 * ProgressFragment - placeholder progress screen.
 */
class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    private val vm: ProgressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.activeGoal.observe(viewLifecycleOwner) { goal ->
            if (goal == null) {
                binding.progressGoalTitle.text = "No active goal"
                binding.progressNumbers.text = "Start a goal to see progress"
                binding.progressRate.progress = 0
                binding.progressRateText.text = "Completion rate: 0%"
                return@observe
            }
            vm.refresh()
        }

        vm.summary.observe(viewLifecycleOwner) { s ->
            if (s == null) return@observe
            binding.progressGoalTitle.text = s.goalTitle
            binding.progressNumbers.text = "${s.completedDays} completed days • Streak ${s.streak} • Day ${s.daysElapsed} of ${s.daysTotal}"
            binding.progressRate.progress = s.completionRate
            binding.progressRateText.text = "Completion rate: ${s.completionRate}%"
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
