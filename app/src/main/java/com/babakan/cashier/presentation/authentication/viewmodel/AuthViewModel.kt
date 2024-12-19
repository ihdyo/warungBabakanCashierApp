package com.babakan.cashier.presentation.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.data.repository.auth.AuthRepository
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
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

    private val _currentUserState = MutableStateFlow<UiState<UserModel>>(UiState.Loading)
    val currentUserState: StateFlow<UiState<UserModel>> get() = _currentUserState

    fun getCurrentUser() {
        _currentUserState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId != null) {
                    val userState = userViewModel.fetchUser(userId)
                    _currentUserState.value = userState
                } else {
                    _currentUserState.value = UiState.Error("Tidak ditemukan", "Tidak ada data untuk pengguna dengan ID: $userId")
                }
            } catch (e: Exception) {
                _currentUserState.value = UiState.Error("Terjadi kesalahan", e.message.toString())
            }
        }
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
                        userViewModel.createUser(it, name, username, email)
                    }
                    _uiState.value = UiState.Success("Registrasi Berhasil!")
                } else {
                    _uiState.value = UiState.Error("Registrasi Gagal!", "Coba lagi nanti.")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Terjadi kesalahan", e.message.toString())
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
                    _uiState.value = UiState.Error("Gagal Masuk", "Email atau kata sandi tidak sesuai.")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Terjadi kesalahan", e.message.toString())
            }
        }
    }

    fun signOut(
        onSuccess: () -> Unit,
        onError: (String) -> Unit = {}
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
