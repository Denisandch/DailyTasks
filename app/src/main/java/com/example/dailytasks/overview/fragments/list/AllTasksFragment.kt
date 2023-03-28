package com.example.dailytasks.overview.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dailytasks.R
import com.example.dailytasks.adapter.TaskAdapter
import com.example.dailytasks.databinding.FragmentAllTasksBinding
import com.example.dailytasks.overview.viewmodel.TaskViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AllTasksFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var binding: FragmentAllTasksBinding
    private val adapter = TaskAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProperties()

        setObserves()

        setOnClickListeners()

        setupMenu()
    }

    private fun initProperties() {
        binding.mainRecycler.adapter = adapter
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
    }

    private fun setObserves() {
        taskViewModel.getAllTasks.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setOnClickListeners() {
        binding.newTaskButton.setOnClickListener {
            findNavController().navigate(R.id.action_allTasksFragment_to_makeTaskFragment)
        }
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                when (menuItem.itemId) {
                    R.id.delete_menu -> deleteTask()

                    R.id.add_item_menu -> findNavController()
                        .navigate(R.id.action_allTasksFragment_to_makeTaskFragment)
                }

                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun deleteTask() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Deleting Tasks")
            .setMessage(R.string.delete_tasks)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Yes") { _, _ ->
                taskViewModel.deleteAllTasks()
            }
            .show()
    }
}