package com.pankaj.employees.data.model

import com.pankaj.employees.domain.model.EmployeePostsItem

data class EmployeePostsItemDTO(
    val id: String,
    val body: String,
    val title: String,
    val userId: String
)

// function to convert DTO to normal object
fun EmployeePostsItemDTO.toEmployeePosts(): EmployeePostsItem {
    return EmployeePostsItem(
        id = this.id ?: "",
        body = this.body ?: "",
        title = this.title ?: "",
        userId = this.userId ?: "",
    )
}