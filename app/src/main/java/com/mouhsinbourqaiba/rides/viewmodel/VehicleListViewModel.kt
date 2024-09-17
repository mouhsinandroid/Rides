package com.mouhsinbourqaiba.rides.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mouhsinbourqaiba.rides.model.Vehicle
import com.mouhsinbourqaiba.rides.repository.VehicleRepository
import kotlinx.coroutines.launch

class VehicleListViewModel : ViewModel() {

    private val repository = VehicleRepository()

    private val _vehicles = MutableLiveData<List<Vehicle>>()
    val vehicles: LiveData<List<Vehicle>> = _vehicles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchVehicles(count: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val fetchedVehicles = repository.getRandomVehicles(count)
                Log.d("fetchedVehicles", fetchedVehicles.toString())
                _vehicles.value = fetchedVehicles.sortedBy { it.vin }
            } catch (e: Exception) {
                _error.value = "Failed to fetch vehicles: ${e.localizedMessage}"
                _vehicles.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}