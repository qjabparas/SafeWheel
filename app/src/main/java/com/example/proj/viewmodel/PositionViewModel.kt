package com.example.proj.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proj.database.dao.PositionDao
import com.example.proj.database.models.Position
import kotlinx.coroutines.flow.Flow


class PositionViewModel(private val positionDao: PositionDao): ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    fun getAllPosition(): Flow<List<Position>> = positionDao.getAll()

    fun getPosition(id: Int): Flow<Position> = positionDao.getPosition(id)

    fun deletePosition(position: Position) {
        positionDao.delete(position)
    }

    private fun insertPosition(position: Position) {
        positionDao.insert(position)
    }

    private fun updatePosition(position: Position) {
        positionDao.insert(position)
    }

    fun addPosition(name: String, status: String) {
        val newPosition = Position(
            name = name,
            status = status)
        insertPosition(newPosition)
    }

    fun updatePosition(id: Int, name: String, status: String) {
        val newPosition = Position(
            id = id,
            name = name,
            status = status,)
        updatePosition(newPosition)
    }

    fun isEntryValid(name: String): Boolean {
        if (name.isBlank())
            return false
        return true
    }


}

class PositionViewModelFactory(private val positionDao: PositionDao): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PositionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PositionViewModel(positionDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
