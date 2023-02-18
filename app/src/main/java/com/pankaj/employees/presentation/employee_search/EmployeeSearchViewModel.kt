package com.pankaj.employees.presentation.employee_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pankaj.employees.common.Resource
import com.pankaj.employees.domain.use_case.SearchEmployeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EmployeeSearchViewModel @Inject constructor(
    private val searchEmployeeUseCase: SearchEmployeeUseCase
) : ViewModel() {


    private val _employeeSearchList = MutableStateFlow(EmployeeSearchState())
    val employeeSearchList: StateFlow<EmployeeSearchState> = _employeeSearchList

    //      Calling the Employee Search Use Case and Handling the response with multiple status
    //      Such as isLoading, error, and handling the data
    fun getEmployees() {
        searchEmployeeUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _employeeSearchList.value = EmployeeSearchState(isLoading = true)
                }
                is Resource.Success -> {
                    _employeeSearchList.value = EmployeeSearchState(data = it.data)
                }
                is Resource.Error -> {
                    _employeeSearchList.value = EmployeeSearchState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }
}