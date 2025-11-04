package com.pampang.nav.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.pampang.nav.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val isLoading = authRepository.isLoading

    private val _registerResult = MutableLiveData<Result<Unit>?>()
    val registerResult: LiveData<Result<Unit>?> get() = _registerResult

    private val _loginResult = MutableLiveData<Result<Unit>?>()
    val loginResult: LiveData<Result<Unit>?> get() = _loginResult

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    private val _loggedInRole = MutableLiveData<String>()
    val loggedInRole: LiveData<String> = _loggedInRole

    private val _changePasswordResult = MutableLiveData<Result<Unit>?>()
    val changePasswordResult: LiveData<Result<Unit>?> get() = _changePasswordResult

    fun login(email: String, password: String, role: String) {
        viewModelScope.launch {
            val result = authRepository.login(email, password, role)
            _loginResult.value = result
        }
    }

    fun register(email: String, password: String, username: String, role: String) {
        viewModelScope.launch {
            if (email.isEmpty() || password.isEmpty() || username.isEmpty() || role.isEmpty()) return@launch

            val result = authRepository.register(email, password, username, role)
            _registerResult.value = result
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun loadUserData() {
        _currentUser.value = authRepository.getCurrentUser()
        _loggedInRole.value = authRepository.getLoggedInRole()
    }

    fun clearRegisterResult() {
        _registerResult.value = null
    }

    fun updateUsername(newUsername: String) {
        viewModelScope.launch {
            authRepository.updateUsername(newUsername)
            loadUserData() // Refresh user data after update
        }
    }

    fun changePassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            val result = authRepository.changePassword(currentPassword, newPassword)
            _changePasswordResult.value = result
        }
    }
}
