package com.example.dailytasks.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dailytasks.R
import com.example.dailytasks.data.Task
import com.example.dailytasks.databinding.OneTaskBinding
import com.example.dailytasks.overview.fragments.list.AllTasksFragmentDirections

class TaskAdapter: ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallback) {

    class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding = OneTaskBinding.bind(view)

        fun init(task: Task) {
            binding.taskBody.text = task.body
            binding.taskTitle.text = task.title
            binding.taskNumber.text = adapterPosition.inc().toString()

            itemView.setOnClickListener {
                val action = AllTasksFragmentDirections.actionAllTasksFragmentToEditFragment(task)
                itemView.findNavController().navigate(action)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.one_task,parent,false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val oneHour = getItem(position)

        holder.init(oneHour)
    }

    object DiffCallback: DiffUtil.ItemCallback<Task>() {

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.body == newItem.body
        }

    }

}