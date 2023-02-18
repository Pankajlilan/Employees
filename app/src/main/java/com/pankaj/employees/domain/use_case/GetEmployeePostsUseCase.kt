package com.pankaj.employees.domain.use_case

import com.pankaj.employees.common.Resource
import com.pankaj.employees.data.model.toEmployeePosts
import com.pankaj.employees.domain.model.EmployeePostsItem
import com.pankaj.employees.domain.repository.EmployeePostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetEmployeePostsUseCase @Inject constructor(private val repository: EmployeePostsRepository) {

    operator fun invoke(): Flow<Resource<List<EmployeePostsItem>>> = flow {
        try {
            emit(Resource.Loading())
//            Converting response data transfer object to normal object and implemented mapping
//            Catching and handling required Exceptions
            val data = repository.getEmployeePosts()
            val domainData =
                if (data.isNotEmpty()) data.map { it.toEmployeePosts() } else emptyList()
            emit(Resource.Success(data = domainData))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An Unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Connectivity"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ("" + e.message)))
        }
    }


}