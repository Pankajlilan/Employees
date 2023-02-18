package com.pankaj.employees.presentation.employee_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.pankaj.employees.R
import com.pankaj.employees.databinding.FragmentEmployeeSearchBinding
import com.pankaj.employees.domain.model.EmployeeDetailsItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EmployeeSearchFragment : Fragment() {

    private val viewModel: EmployeeSearchViewModel by viewModels()
    var list = mutableListOf<EmployeeDetailsItem>()

    private var _binding: FragmentEmployeeSearchBinding? = null
    private val binding: FragmentEmployeeSearchBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmployeeSearchBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        Calling an API to get the list of the employees
        viewModel.getEmployees()
        binding.employeeIdEdt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                validateData()
            }
            false
        }

        binding.employeeIdEdt.addTextChangedListener {
            binding.textInputLayout.error = null
        }

        binding.btnContinue.setOnClickListener {
            val id = validateData()
            if (id.isNotBlank()) {
//                Storing the name and company of the employee
                val employee = list.find { employee -> id == employee.id }
                findNavController().navigate(
                    EmployeeSearchFragmentDirections.navigateToEmployeePostsFragment(
                        employeeId = id,
                        employeeName = employee?.name,
                        employeeCompany = employee?.company
                    )
                )
            }
        }

//      Handling the response inside the coroutine scope with multiple status to handle
//      Such as isLoading, error, and handling the data

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.employeeSearchList.collect {
                if (it.isLoading) {
                    binding.nothingFound.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                if (it.error.isNotBlank()) {
                    binding.nothingFound.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }

                it.data?.let {
                    if (it.isEmpty()) {
                        binding.nothingFound.visibility = View.VISIBLE
                    }
                    binding.progressBar.visibility = View.GONE
                    list = it.toMutableList()
                }
            }
        }
    }

    private fun validateData(): String {
//      Validating multiple scenarios that user id cannot be empty, User Id should be mentioned
//      mandatory and if the entered user exists or not.
        val searchId =
            list.find { employee -> binding.employeeIdEdt.text.toString() == employee.id }?.id
        return if (searchId != null) {
            binding.textInputLayout.error = null
            searchId
        } else if (binding.employeeIdEdt.text.toString().isEmpty()) {
            binding.textInputLayout.error = getString(R.string.user_idnot_empty)
            ""
        } else {
            binding.textInputLayout.error = getString(R.string.user_id_not_found)
            ""
        }
    }
}