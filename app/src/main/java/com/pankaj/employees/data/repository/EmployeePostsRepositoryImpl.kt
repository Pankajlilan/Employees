package com.pankaj.employees.data.repository

import com.pankaj.employees.data.model.EmployeePostsItemDTO
import com.pankaj.employees.data.remote.EmployeeAPI
import com.pankaj.employees.domain.repository.EmployeePostsRepository

class EmployeePostsRepositoryImpl(private val employeeAPI: EmployeeAPI) : EmployeePostsRepository {

//    Implement the Employee Posts Interface to call the Employee API
    override suspend fun getEmployeePosts(): List<EmployeePostsItemDTO> {
        return employeeAPI.getEmployeePosts()
    }
}