package com.babakan.cashier.presentation.authentication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.data.repository.auth.AuthRepository
import com.babakan.cashier.data.state.UiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userViewModel: UserViewModel = UserViewModel()
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val uiState: StateFlow<UiState<String>> get() = _uiState

    fun checkUserSession(): Boolean {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser != null
    }

    fun register(
        name: String,
        username: String,
        email: String,
        password: String
    ) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val result = authRepository.signUp(name, username, email, password)
                if (result) {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    userId?.let {
                        userViewModel.saveUserData(it, name, username, email)
                    }
                    _uiState.value = UiState.Success("Registrasi Berhasil!")
                } else {
                    _uiState.value = UiState.Error(
                        title = "Registration Gagal!",
                        message = "Coba lagi nanti."
                    )
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    title = "Kesalahan Registrasi",
                    message = "Terdapat kesalahan: ${e.message}"
                )
            }
        }
    }

    fun login(
        email: String,
        password: String
    ) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val result = authRepository.login(email, password)
                if (result) {
                    _uiState.value = UiState.Success("Berhasil Masuk!")
                } else {
                    _uiState.value = UiState.Error(
                        title = "Gagal Masuk",
                        message = "Email atau kata sandi tidak sesuai."
                    )
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    title = "Gagal Masuk",
                    message = "Terdapat kesalahan: ${e.message}"
                )
            }
        }
    }

    fun signOut(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                auth.signOut()
                onSuccess()
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Gagal Keluar")
            }
        }
    }
}
