package com.babakan.cashier.presentation.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.data.repository.auth.AuthRepository
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _authState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val authState: StateFlow<UiState<String>> get() = _authState

    private val _currentUserState = MutableStateFlow<UiState<UserModel>>(UiState.Idle)
    val currentUserState: StateFlow<UiState<UserModel>> get() = _currentUserState

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        _currentUserState.value = UiState.Loading
        viewModelScope.launch {
            _currentUserState.value = authRepository.getCurrentUser()
        }
    }

    fun registerUser(
        name: String,
        username: String,
        email: String,
        password: String
    ) {
        _authState.value = UiState.Loading
        viewModelScope.launch {
            _authState.value = authRepository.signUpUser(name, username, email, password)
        }
    }

    fun loginUser(
        email: String,
        password: String
    ) {
        _authState.value = UiState.Loading
        viewModelScope.launch {
            _authState.value = authRepository.loginUser(email, password)
        }
    }

    fun signOutUser() {
        viewModelScope.launch {
            _authState.value = authRepository.signOutUser()
        }
    }
}