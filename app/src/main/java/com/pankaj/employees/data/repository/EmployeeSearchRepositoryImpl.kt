package com.pankaj.employees.data.repository

import com.pankaj.employees.data.model.EmployeeDetailsItemDTO
import com.pankaj.employees.data.remote.EmployeeAPI
import com.pankaj.employees.domain.repository.EmployeeSearchRepository

class EmployeeSearchRepositoryImpl(private val employeeAPI: EmployeeAPI) :
    EmployeeSearchRepository {

//    Implement the Employee Search Interface to call the Employee API
    override suspend fun getEmployee(): List<EmployeeDetailsItemDTO> {
        return employeeAPI.getEmployeeList()
    }
}