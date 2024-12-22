package com.babakan.cashier.presentation.owner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.data.repository.user.UserRepository
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _fetchUsersState = MutableStateFlow<UiState<List<UserModel>>>(UiState.Idle)
    val fetchUsersState: StateFlow<UiState<List<UserModel>>> = _fetchUsersState

    private val _fetchUserByIdState = MutableStateFlow<UiState<UserModel>>(UiState.Idle)
    val fetchUserByIdState: StateFlow<UiState<UserModel>> = _fetchUserByIdState

    private val _createUserState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val createUserState: StateFlow<UiState<Unit>> = _createUserState

    private val _updateUserState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val updateUserState: StateFlow<UiState<Unit>> = _updateUserState

    private val _deleteUserState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val deleteUserState: StateFlow<UiState<Unit>> = _deleteUserState

    private val _searchUserByName = MutableStateFlow<UiState<List<UserModel>>>(UiState.Idle)
    val searchUserByName: StateFlow<UiState<List<UserModel>>> = _searchUserByName

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        _fetchUsersState.value = UiState.Loading
        viewModelScope.launch {
            _fetchUsersState.value = userRepository.getUsers()
        }
    }

    fun fetchUserById(
        userId: String
    ) {
        _fetchUserByIdState.value = UiState.Loading
        viewModelScope.launch {
            _fetchUserByIdState.value = userRepository.getUserById(userId)
        }
    }

    fun createUser(
        userData: UserModel
    ) {
        _createUserState.value = UiState.Loading
        viewModelScope.launch {
            _createUserState.value = userRepository.setUserById(userData.id, userData)
        }
    }

    fun updateUserById(
        userId: String,
        fieldName: String,
        newValue: Any
    ) {
        _updateUserState.value = UiState.Loading
        viewModelScope.launch {
            _updateUserState.value = userRepository.updateUserById(userId, fieldName, newValue)
        }
    }

    fun deleteUserById(
        userId: String
    ) {
        _deleteUserState.value = UiState.Loading
        viewModelScope.launch {
            _deleteUserState.value = userRepository.deleteUserById(userId)
        }
    }

    fun searchUsersByName(
        query: String
    ) {
        _searchUserByName.value = UiState.Loading
        viewModelScope.launch {
            _searchUserByName.value = userRepository.searchUsersByName(query)
        }
    }
}