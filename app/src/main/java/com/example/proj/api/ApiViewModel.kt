package com.example.proj.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ApiViewModel: ViewModel() {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    init {
        getApiData()
    }

    private fun getApiData() {
        viewModelScope.launch {
            try {
                val result = PositionApi.retrofitService.getData()

                _status.value = result.status
                _name.value = result.name

            }
            catch (e: Exception) {
                Log.d("API","Error: ${e.message}")
            }
        }
    }
}