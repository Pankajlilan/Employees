package com.pankaj.employees.data.model

import com.pankaj.employees.domain.model.EmployeeDetailsItem

data class EmployeeDetailsItemDTO(
    val company: CompanyDTO,
    val email: String,
    val id: String,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)

// function to convert DTO to normal object
fun EmployeeDetailsItemDTO.toEmployeeDetail(): EmployeeDetailsItem {
    return EmployeeDetailsItem(
        company = this.company.name ?: "",
        email = this.email ?: "",
        id = this.id ?: "",
        name = this.name ?: "",
        phone = this.phone ?: "",
        username = this.username ?: "",
        website = this.website ?: "",
    )
}