package com.pankaj.employees.domain.use_case

import com.pankaj.employees.common.Resource
import com.pankaj.employees.data.model.toEmployeeDetail
//import com.pankaj.employees.data.model.toSingleEmployee
//import com.pankaj.employees.domain.model.Employee
import com.pankaj.employees.domain.model.EmployeeDetailsItem
import com.pankaj.employees.domain.repository.EmployeeSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchEmployeeUseCase @Inject constructor(private val repository: EmployeeSearchRepository) {


    operator fun invoke(): Flow<Resource<List<EmployeeDetailsItem>>> = flow {
        try {
            emit(Resource.Loading())
//            Converting response data transfer object to normal object and implemented mapping
//            Catching and handling required Exceptions
            val data = repository.getEmployee()
            val domainData = if (data != null) data.map { it.toEmployeeDetail() } else emptyList()
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