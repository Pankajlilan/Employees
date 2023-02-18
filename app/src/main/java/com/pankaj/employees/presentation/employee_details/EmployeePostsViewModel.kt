package com.pankaj.employees.presentation.employee_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pankaj.employees.common.Resource
import com.pankaj.employees.domain.use_case.GetEmployeePostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EmployeePostsViewModel @Inject constructor(private val employeePostsUseCase: GetEmployeePostsUseCase) :
    ViewModel() {

    private val _employeePosts = MutableStateFlow(EmployeePostsState())
    val employeePosts: StateFlow<EmployeePostsState> = _employeePosts

//      Calling the Employee Post Use Case and Handling the response with multiple status
//      Such as isLoading, error, and handling the data
      fun getEmployeePosts() {
        employeePostsUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _employeePosts.value = EmployeePostsState(isLoading = true)
                }
                is Resource.Success -> {
                    _employeePosts.value = EmployeePostsState(data = it.data)
                }
                is Resource.Error -> {
                    _employeePosts.value = EmployeePostsState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }
}