package com.pankaj.employees.domain.repository

import com.pankaj.employees.data.model.EmployeeDetailsItemDTO

interface EmployeeSearchRepository {
    suspend fun getEmployee(): List<EmployeeDetailsItemDTO>
}