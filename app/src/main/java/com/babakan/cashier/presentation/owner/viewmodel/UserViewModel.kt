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

    private val _addUserState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val addUserState: StateFlow<UiState<Unit>> = _addUserState

    private val _fetchUserByIdState = MutableStateFlow<UiState<UserModel>>(UiState.Idle)
    val fetchUserByIdState: StateFlow<UiState<UserModel>> = _fetchUserByIdState

    private val _updateUserState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val updateUserState: StateFlow<UiState<Unit>> = _updateUserState

    private val _deleteUserState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val deleteUserState: StateFlow<UiState<Unit>> = _deleteUserState

    private val _searchUserByName = MutableStateFlow<UiState<List<UserModel>>>(UiState.Idle)
    val searchUserByName: StateFlow<UiState<List<UserModel>>> = _searchUserByName

    init {
        fetchUsers()
    }

    fun resetAuditState() {
        _addUserState.value = UiState.Idle
        _updateUserState.value = UiState.Idle
        _deleteUserState.value = UiState.Idle
    }

    fun resetSearchState() {
        _searchUserByName.value = UiState.Idle
    }

    fun fetchUsers() {
        _fetchUsersState.value = UiState.Loading
        viewModelScope.launch {
            _fetchUsersState.value = userRepository.getUsers()
        }
    }

    fun createUser(
        userData: UserModel
    ) {
        _addUserState.value = UiState.Loading
        viewModelScope.launch {
            _addUserState.value = userRepository.createUser(userData)
        }
    }

    fun updateUser(
        userId: String,
        userData: UserModel
    ) {
        _updateUserState.value = UiState.Loading
        viewModelScope.launch {
            _updateUserState.value = userRepository.updateUser(userId, userData)
        }
    }

    fun deleteUser(
        userId: String
    ) {
        _deleteUserState.value = UiState.Loading
        viewModelScope.launch {
            _deleteUserState.value = userRepository.deleteUser(userId)
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

    fun searchUsersByName(
        query: String
    ) {
        _searchUserByName.value = UiState.Loading
        viewModelScope.launch {
            _searchUserByName.value = userRepository.searchUsersByName(query)
        }
    }
}