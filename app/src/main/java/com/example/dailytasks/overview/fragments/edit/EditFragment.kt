package com.example.dailytasks.overview.fragments.edit

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
import androidx.navigation.fragment.navArgs
import com.example.dailytasks.R
import com.example.dailytasks.data.Task
import com.example.dailytasks.databinding.FragmentMakeTaskBinding
import com.example.dailytasks.overview.viewmodel.TaskViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class EditFragment : Fragment() {

    private val args by navArgs<EditFragmentArgs>()
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

        initScreen()

        setupMenu()
    }

    private fun initScreen() {
        binding.bodyText.setText(args.currentTask.body)
        binding.titleText.setText(args.currentTask.title)

    }

    private fun initProperties() {
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
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

                    R.id.add_item_menu -> updateTask()

                    android.R.id.home -> closeFragment()
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
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Yes") { _, _ ->
                taskViewModel.deleteOneTask(makeTask())
                closeFragment()
            }
            .show()
    }

    private fun updateTask() {
        if (isFieldsEmpty()) {
            Toast.makeText(requireContext(), R.string.empty_body, Toast.LENGTH_SHORT).show()
        } else {
            taskViewModel.updateTask(makeTask())
            closeFragment()
        }
    }

    private fun isFieldsEmpty() = binding.bodyText.text.toString().isEmpty()

    private fun makeTask(): Task {

        val title = if (isTitleEmpty()) {
            binding.bodyText.text.toString().substringBefore(" ")
        } else {
            binding.titleText.text.toString()
        }
        val body = binding.bodyText.text.toString()

        return Task(args.currentTask.id, title, body)
    }

    private fun isTitleEmpty() = binding.titleText.text.toString().isEmpty()

    private fun closeFragment() {
        findNavController().navigate(R.id.action_editFragment_to_allTasksFragment)
    }
}