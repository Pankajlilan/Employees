package com.pankaj.employees.presentation.employee_details

import com.pankaj.employees.domain.model.EmployeePostsItem

data class EmployeePostsState(
    val isLoading: Boolean = false,
    val data: List<EmployeePostsItem>? = null,
    val error: String = ""
)