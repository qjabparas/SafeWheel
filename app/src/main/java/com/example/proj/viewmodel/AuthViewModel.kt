package com.example.proj.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proj.database.dao.UserDao
import com.example.proj.database.models.User
import kotlinx.coroutines.launch

class AuthViewModel(private val userDao: UserDao): ViewModel() {
    private fun getUser(username: String, password: String): User? = userDao.getUser(username, password)

    fun isEntryValid(username: String, password: String): Boolean {
        if (username.isBlank() || password.isBlank())
            return false
        return true
    }

    fun userExist(username: String, password: String): Boolean {
        val user: User? = getUser(username, password)
        if (user != null)
            return true
        return false
    }


    private fun insertUser(user: User) {
        viewModelScope.launch{
            userDao.insertUser(
                user = user
            )
        }
    }

    fun isRegisterEntryValid(username: String, password: String, confirmPassword: String): Boolean {
        if (username.isBlank() || password.isBlank() || confirmPassword.isBlank() )
            return false
        return true
    }

    fun isPasswordSimilar(password: String, confirmPassword: String): Boolean {
        if (password == confirmPassword)
            return true
        return false
    }

    private fun createNewUser(username: String, password: String): User{
        return User(
            username = username,
            password = password
        )
    }

    fun newUserEntry(username: String, password: String){
        val user = createNewUser(username, password)
        insertUser((user))
    }
}

class AuthViewModelFactory(private val userDao: UserDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}