package com.orderpos.repository


import com.orderpos.data.local.dao.EmployeeDao
import com.orderpos.data.local.entities.Employee
import kotlinx.coroutines.flow.Flow

class EmployeeRepository(private val employeeDao: EmployeeDao) {
    suspend fun insertEmployee(employee: Employee) {
        employeeDao.insert(employee)
    }

    fun getAllEmployees(): Flow<List<Employee>> = employeeDao.getAllEmployees()

}
