package com.babakan.cashier.presentation.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.data.repository.auth.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _isUserActive = MutableStateFlow<Boolean?>(null)
    val isUserActive: StateFlow<Boolean?> = _isUserActive

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId

    init {
        observeAuthState()
    }

    private fun observeAuthState() {
        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            val user = auth.currentUser
            if (user != null) {
                _userId.value = user.uid
                _isLoggedIn.value = true
                observeIsActiveField(user.uid)
            } else {
                _isLoggedIn.value = false
                _userId.value = null
                _isUserActive.value = null
                _isLoading.value = false
            }
        }
    }

    fun observeIsActiveField(userId: String) {
        viewModelScope.launch {
            userRepository.observeIsActiveField(userId).collectLatest { isActive ->
                _isUserActive.value = isActive
                _isLoading.value = false
                if (!isActive) {
                    FirebaseAuth.getInstance().signOut()
                    _isLoggedIn.value = false
                    _userId.value = null
                }
            }
        }
    }
}
