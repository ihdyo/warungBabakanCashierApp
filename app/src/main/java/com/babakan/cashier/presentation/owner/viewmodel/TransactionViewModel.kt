package com.babakan.cashier.presentation.owner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.data.repository.transaction.TransactionRepository
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.TransactionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val transactionRepository: TransactionRepository = TransactionRepository()
) : ViewModel() {

    private val _fetchTransactionsState = MutableStateFlow<UiState<List<TransactionModel>>>(UiState.Idle)
    val fetchTransactionsState: StateFlow<UiState<List<TransactionModel>>> = _fetchTransactionsState

    private val _fetchTransactionByIdState = MutableStateFlow<UiState<TransactionModel>>(UiState.Idle)
    val fetchTransactionByIdState: StateFlow<UiState<TransactionModel>> = _fetchTransactionByIdState

    private val _createTransactionState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val createTransactionState: StateFlow<UiState<Unit>> = _createTransactionState

    private val _updateTransactionState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val updateTransactionState: StateFlow<UiState<Unit>> = _updateTransactionState

    private val _deleteTransactionState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val deleteTransactionState: StateFlow<UiState<Unit>> = _deleteTransactionState

    fun fetchTransactions() {
        _fetchTransactionsState.value = UiState.Loading
        viewModelScope.launch {
            _fetchTransactionsState.value = transactionRepository.getTransactions()
        }
    }

    fun fetchTransactionById(
        transactionId: String
    ) {
        _fetchTransactionByIdState.value = UiState.Loading
        viewModelScope.launch {
            _fetchTransactionByIdState.value = transactionRepository.getTransactionById(transactionId)
        }
    }

    fun createTransaction(
        transactionData: TransactionModel
    ) {
        _createTransactionState.value = UiState.Loading
        viewModelScope.launch {
            _createTransactionState.value = transactionRepository.setTransactionById(transactionData.transactionId, transactionData)
        }
    }

    fun updateTransactionById(
        transactionId: String,
        fieldName: String,
        newValue: Any
    ) {
        _updateTransactionState.value = UiState.Loading
        viewModelScope.launch {
            _updateTransactionState.value = transactionRepository.updateTransactionById(transactionId, fieldName, newValue)
        }
    }

    fun deleteTransactionById(
        transactionId: String
    ) {
        _deleteTransactionState.value = UiState.Loading
        viewModelScope.launch {
            _deleteTransactionState.value = transactionRepository.deleteTransactionById(transactionId)
        }
    }
}