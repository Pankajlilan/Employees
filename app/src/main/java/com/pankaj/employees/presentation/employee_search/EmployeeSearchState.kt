package com.pankaj.employees.presentation.employee_search

import com.pankaj.employees.domain.model.EmployeeDetailsItem

data class EmployeeSearchState(
    val isLoading: Boolean = false,
    val data: List<EmployeeDetailsItem>? = null,
    val error: String = ""
)