package com.example.smartcash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    private val _isLoggedIn = MutableLiveData(false)
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    fun login(username: String, password: String): Boolean {
        val success = username == VALID_USER && password == VALID_PASSWORD
        _isLoggedIn.value = success
        return success
    }

    fun logout() {
        _isLoggedIn.value = false
    }

    companion object {
        private const val VALID_USER = "admin"
        private const val VALID_PASSWORD = "1234"
    }
}

