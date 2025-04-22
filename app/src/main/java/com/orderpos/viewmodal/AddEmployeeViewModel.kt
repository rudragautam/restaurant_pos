package com.orderpos.viewmodal


import android.app.Application
import android.app.DatePickerDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.orderpos.data.local.AppDatabase
import com.orderpos.data.local.entities.Employee
import com.orderpos.data.local.entities.Restaurant
import com.orderpos.repository.EmployeeRepository
import com.orderpos.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class AddEmployeeViewModel(application: Application) : AndroidViewModel(application) {

    private val employeeRepository: EmployeeRepository
    private val restaurantRepository: RestaurantRepository

    val employeeList: LiveData<List<Employee>>

    val selectedRestaurantName = MutableLiveData<String>()
    val restaurantId = MutableLiveData<Long>()
    val name = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val role = MutableLiveData<String>()
    val pinCode = MutableLiveData<String>()
    val hireDate = MutableLiveData<Long>()
    val hireDateText = MutableLiveData<String>()
    val isActive = MutableLiveData<Boolean>(true)
    val restaurantList = MutableLiveData<List<Restaurant>>()
    val successMessage = MutableLiveData<String>()

    init {
        val db = AppDatabase.getDatabase(application)
        employeeRepository = EmployeeRepository(db.employeeDao())
        restaurantRepository = RestaurantRepository(db.restaurantDao())
        employeeList = employeeRepository.getAllEmployees().asLiveData()
        fetchRestaurants()
    }

    fun addEmployee() {
        val employee = Employee(
            restaurantId = restaurantId.value ?: 0L,
            name = name.value ?: "",
            phone = phone.value ?: "",
            email = email.value ?: "",
            role = role.value ?: "",
            pinCode = pinCode.value ?: "",
            isActive = isActive.value ?: true,
            hireDate = hireDate.value ?: System.currentTimeMillis()
        )

        viewModelScope.launch(Dispatchers.IO) {
            employeeRepository.insertEmployee(employee)
            successMessage.postValue("Employee added!")
        }
    }

    fun fetchRestaurants() {
        viewModelScope.launch {
            restaurantRepository.getAllRestaurants().collect { restaurants ->
                restaurantList.postValue(restaurants)
            }
        }
    }

    fun onSaveClicked() {
        addEmployee()
    }

/*    fun onPickHireDateClicked() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(requireContext(), { _, y, m, d ->
            calendar.set(y, m, d)
            viewModel.hireDate.value = calendar.timeInMillis
            viewModel.hireDateText.value = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        }, year, month, day)

        dialog.show()
    }*/

    interface EmployeeClickHandler {
        fun onSaveClicked()
        fun onPickHireDateClicked()
    }
}


