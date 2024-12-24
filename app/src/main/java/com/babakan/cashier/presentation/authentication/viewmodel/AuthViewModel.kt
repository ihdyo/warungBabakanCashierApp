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

    private val _registerState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val registerState: StateFlow<UiState<String>> get() = _registerState

    private val _loginState = MutableStateFlow<UiState<Pair<String, String>>>(UiState.Idle)
    val loginState: StateFlow<UiState<Pair<String, String>>> get() = _loginState

    private val _signOutState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val signOutState: StateFlow<UiState<String>> get() = _signOutState

    private val _isUserSignedIn = MutableStateFlow(false)
    val isUserSignedIn: StateFlow<Boolean> get() = _isUserSignedIn

    private val _currentUserState = MutableStateFlow<UiState<UserModel>>(UiState.Idle)
    val currentUserState: StateFlow<UiState<UserModel>> get() = _currentUserState

    private val _isUserActive = MutableStateFlow<Boolean?>(null)
    val isUserActive: StateFlow<Boolean?> = _isUserActive

    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            _currentUserState.value = UiState.Loading
            val result = authRepository.getCurrentUser()
            _currentUserState.value = result
        }
    }

    fun checkIsUserSignedIn() {
        viewModelScope.launch {
            val signedIn = authRepository.isUserSignedIn()
            _isUserSignedIn.value = signedIn
            if (signedIn) {
                _currentUserState.value = authRepository.getCurrentUser()
            }
            _isLoading.value = false
        }
    }

    private fun observeUserStatus(
        userId: String
    ) {
        viewModelScope.launch {
            userRepository.listenCurrentUserIsActive(userId).collectLatest { isActive ->
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
            _registerState.value = UiState.Loading
            val result = authRepository.signUpAuth(name, username, email, password)
            _registerState.value = result
            signOutUser()
        }
    }

    fun loginUser(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading

            val result = authRepository.loginAuth(email, password)
            _loginState.value = result

            if (result is UiState.Success) {
                val (userId, message) = result.data
                checkIsUserSignedIn()
                observeUserStatus(userId)
            }
        }
    }

    fun signOutUser() {
        viewModelScope.launch {
            _signOutState.value = UiState.Loading
            val result = authRepository.signOutAuth()
            _signOutState.value = result

            if (result is UiState.Success) {
                checkIsUserSignedIn()
            }
        }
    }
}
