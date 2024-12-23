package com.babakan.cashier.presentation.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.data.repository.auth.AuthRepository
import com.babakan.cashier.data.repository.user.UserRepository
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _authState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val authState: StateFlow<UiState<String>> get() = _authState

    private val _signOutState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val signOutState: StateFlow<UiState<String>> get() = _signOutState

    private val _isUserSignedIn = MutableStateFlow(false)
    val isUserSignedIn: StateFlow<Boolean> get() = _isUserSignedIn

    private val _currentUserState = MutableStateFlow<UiState<UserModel>>(UiState.Idle)
    val currentUserState: StateFlow<UiState<UserModel>> get() = _currentUserState

    private val _isUserActive = MutableStateFlow<Boolean?>(null)
    val isUserActive: StateFlow<Boolean?> = _isUserActive

    fun checkIsUserSignedIn() {
        viewModelScope.launch {
            val signedIn = authRepository.isUserSignedIn()
            _isUserSignedIn.value = signedIn
            if (signedIn) {
                _currentUserState.value = authRepository.getCurrentUser()
                observeUserStatus()
            }
            _isLoading.value = false
        }
    }

    private fun observeUserStatus() {
        viewModelScope.launch {
            userRepository.listenCurrentUserIsActive().collectLatest { isActive ->
                _isUserActive.value = isActive
                if (!isActive) {
                    signOutUser()
                }
            }
        }
    }

    fun registerUser(
        name: String,
        username: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _authState.value = UiState.Loading
            val result = authRepository.signUpUser(name, username, email, password)
            _authState.value = result

            if (result is UiState.Success) {
                checkIsUserSignedIn()
            }
        }
    }

    fun loginUser(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _authState.value = UiState.Loading
            val result = authRepository.loginUser(email, password)
            _authState.value = result

            if (result is UiState.Success) {
                checkIsUserSignedIn()
                observeUserStatus()
            }
        }
    }

    fun signOutUser() {
        viewModelScope.launch {
            _signOutState.value = UiState.Loading
            val result = authRepository.signOutUser()
            _signOutState.value = result

            if (result is UiState.Success) {
                checkIsUserSignedIn()
            }
        }
    }
}
