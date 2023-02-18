package com.pankaj.employees.hilt

import com.pankaj.employees.common.Constants
import com.pankaj.employees.data.remote.EmployeeAPI
import com.pankaj.employees.data.repository.EmployeePostsRepositoryImpl
import com.pankaj.employees.data.repository.EmployeeSearchRepositoryImpl
import com.pankaj.employees.domain.repository.EmployeePostsRepository
import com.pankaj.employees.domain.repository.EmployeeSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HIltModules {

    @Singleton
    @Provides
    fun provideEmployeeSearchAPI(): EmployeeAPI {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(EmployeeAPI::class.java)
    }

    @Provides
    fun provideEmployeeSearchRepository(employeeAPI: EmployeeAPI): EmployeeSearchRepository {
        return EmployeeSearchRepositoryImpl(employeeAPI)
    }

    @Provides
    fun provideEmployeePosts(employeeAPI: EmployeeAPI): EmployeePostsRepository {
        return EmployeePostsRepositoryImpl(employeeAPI)
    }

}