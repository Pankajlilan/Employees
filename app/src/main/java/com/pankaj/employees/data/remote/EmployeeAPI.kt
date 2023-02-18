package com.pankaj.employees.data.remote

import com.pankaj.employees.data.model.EmployeeDetailsItemDTO
import com.pankaj.employees.data.model.EmployeePostsItemDTO
import retrofit2.http.GET

interface EmployeeAPI {

    //  API to get list of users
    @GET("users")
    suspend fun getEmployeeList(): List<EmployeeDetailsItemDTO>

    //  API to get list of posts
    @GET("posts")
    suspend fun getEmployeePosts(): List<EmployeePostsItemDTO>
}