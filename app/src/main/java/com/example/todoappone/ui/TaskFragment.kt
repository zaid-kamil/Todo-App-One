package com.example.todoappone.ui

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.todoappone.R
import com.example.todoappone.TodoApplication
import com.example.todoappone.data.Task
import com.example.todoappone.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {

    companion object {
        fun newInstance() = TaskFragment()
    }

    private lateinit var binding: FragmentTaskBinding
    private val viewModel: TaskViewModel by activityViewModels {
        InventoryViewModelFactory((activity?.application as TodoApplication).database.taskDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAdd.setOnClickListener { showAddTaskDialog(it) }
    }

    private fun showAddTaskDialog(it: View?) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add Task")
            .setView(R.layout.add_task_dialog_layout)
            .setPositiveButton("Add") { dialog, _ ->
                val taskName =
                    (dialog as AlertDialog).findViewById<EditText>(R.id.editTitle)?.text.toString()
                if(viewModel.addItem(title = taskName)){
                    Toast.makeText(requireContext(), "Task Added", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
        dialog.show()
    }

}