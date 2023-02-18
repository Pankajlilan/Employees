package com.pankaj.employees.domain.repository

import com.pankaj.employees.data.model.EmployeePostsItemDTO

interface EmployeePostsRepository {
    suspend fun getEmployeePosts(): List<EmployeePostsItemDTO>
}