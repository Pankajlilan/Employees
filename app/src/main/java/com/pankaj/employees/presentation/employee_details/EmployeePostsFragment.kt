package com.pankaj.employees.presentation.employee_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pankaj.employees.databinding.FragmentEmployeePostsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class EmployeePostsFragment : Fragment() {

    private var _binding: FragmentEmployeePostsBinding? = null
    private val binding: FragmentEmployeePostsBinding
        get() = _binding!!

    private val viewModel: EmployeePostsViewModel by viewModels()
    private val adapter = EmployeePostsAdapter()
    private val args: EmployeePostsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmployeePostsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        Calling an API to get the posts if the employee id is not empty
        args.employeeId?.let {
            viewModel.getEmployeePosts()
        }

//      Handling the response inside the coroutine scope with multiple status to handle
//      Such as isLoading, error, and handling the data
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.employeePosts.collect {
                if (it.isLoading) {
                }
                if (it.error.isNotBlank()) {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }
                it.data?.let {
//                    Filtering the data based on user's id from the userId field of posts data
                    adapter.list = it.filter { it.userId == args.employeeId }.toMutableList()
                    adapter.employeeName = args.employeeName.toString()
                    adapter.employeeCompany = args.employeeCompany.toString()
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.adapter = adapter

    }
}