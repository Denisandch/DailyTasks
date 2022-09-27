package com.example.dailytasks.overview.fragments.make

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dailytasks.R
import com.example.dailytasks.data.Task
import com.example.dailytasks.databinding.FragmentMakeTaskBinding
import com.example.dailytasks.overview.viewmodel.TaskViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MakeTaskFragment : Fragment() {

    private lateinit var binding: FragmentMakeTaskBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMakeTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProperties()

        setupMenu()
    }

    private fun initProperties() {
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)

                changeMenu(menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                when (menuItem.itemId) {

                    R.id.delete_menu -> deleteTask()

                    android.R.id.home -> closeFragment()

                    R.id.add_item_menu -> addTask()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun changeMenu(menu: Menu) {
        menu.getItem(0).title = "Delete current task"
        menu.getItem(1).icon = ContextCompat.getDrawable(requireContext(), R.drawable.ready)
    }

    private fun deleteTask() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Deleting Task")
            .setMessage(R.string.delete_task)
            .setNegativeButton("Cancel") { _, _ ->}
            .setPositiveButton("Yes") { _, _ ->
                closeFragment()
            }
            .show()
    }

    private fun addTask() {
        if(isFieldsEmpty()) {
            Toast.makeText(requireContext(), R.string.empty_body, Toast.LENGTH_SHORT).show()
        } else {
            taskViewModel.addTask(makeTask())
            closeFragment()
        }
    }

    private fun isFieldsEmpty() = binding.bodyText.text.toString().isEmpty()

    private fun isTitleEmpty() = binding.titleText.text.toString().isEmpty()

    private fun makeTask(): Task {

        val title = if(isTitleEmpty()) {
            binding.bodyText.text.toString().substringBefore(" ")
        } else {
            binding.titleText.text.toString()
        }
        val body = binding.bodyText.text.toString()

        return Task(0, title, body)
    }

    private fun closeFragment() {
        findNavController().navigate(R.id.action_makeTaskFragment_to_allTasksFragment)
    }
}