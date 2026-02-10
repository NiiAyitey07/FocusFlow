package com.focusflow.app.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.focusflow.app.data.model.Task
import com.focusflow.app.databinding.ItemTaskBinding

class TaskAdapter(
    private val onToggle: (Task, Boolean) -> Unit,
    private val onDelete: (Task) -> Unit,
    private val onEdit: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskVH>(Diff()) {

    class Diff : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem
    }

    inner class TaskVH(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.taskTitle.text = task.title
            binding.taskDesc.text = task.description ?: ""
            binding.taskDesc.isEnabled = task.description != null
            binding.taskDesc.alpha = if (task.description.isNullOrBlank()) 0f else 0.85f

            binding.taskCheck.setOnCheckedChangeListener(null)
            binding.taskCheck.isChecked = task.isCompleted
            binding.taskCheck.setOnCheckedChangeListener { _, isChecked ->
                onToggle(task, isChecked)
            }

            binding.btnDelete.setOnClickListener { onDelete(task) }
            binding.btnEdit.setOnClickListener { onEdit(task) }
            binding.root.setOnClickListener { onEdit(task) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskVH(binding)
    }

    override fun onBindViewHolder(holder: TaskVH, position: Int) {
        holder.bind(getItem(position))
    }
}
