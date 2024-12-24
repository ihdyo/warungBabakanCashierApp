package com.babakan.cashier.presentation.owner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.data.repository.transaction.TransactionRepository
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.babakan.cashier.presentation.owner.model.TransactionModel
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val transactionRepository: TransactionRepository = TransactionRepository()
) : ViewModel() {

    private val _fetchTransactionsState = MutableStateFlow<UiState<List<TransactionModel>>>(UiState.Idle)
    val fetchTransactionsState: StateFlow<UiState<List<TransactionModel>>> = _fetchTransactionsState

    private val _transactionByIdState = MutableStateFlow<UiState<TransactionModel>>(UiState.Idle)
    val transactionByIdState: StateFlow<UiState<TransactionModel>> = _transactionByIdState

    private val _createTransactionState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val createTransactionState: StateFlow<UiState<Unit>> = _createTransactionState

    private val _searchTransactionIdState = MutableStateFlow<UiState<List<TransactionModel>>>(UiState.Idle)
    val searchTransactionIdState: StateFlow<UiState<List<TransactionModel>>> = _searchTransactionIdState

    private val _searchTransactionNameState = MutableStateFlow<UiState<List<TransactionModel>>>(UiState.Idle)
    val searchTransactionNameState: StateFlow<UiState<List<TransactionModel>>> = _searchTransactionNameState

    private val _searchTransactionDateRangeState = MutableStateFlow<UiState<List<TransactionModel>>>(UiState.Idle)
    val searchTransactionDateRangeState: StateFlow<UiState<List<TransactionModel>>> = _searchTransactionDateRangeState

    init {
        fetchTransactions()
    }

    fun resetSearchState() {
        _searchTransactionIdState.value = UiState.Idle
        _searchTransactionNameState.value = UiState.Idle
        _searchTransactionDateRangeState.value = UiState.Idle
    }

    private fun fetchTransactions() {
        _fetchTransactionsState.value = UiState.Loading
        viewModelScope.launch {
            _fetchTransactionsState.value = transactionRepository.getTransactions()
        }
    }

    fun fetchTransactionById(
        transactionId: String
    ) {
        _transactionByIdState.value = UiState.Loading
        viewModelScope.launch {
            _transactionByIdState.value = transactionRepository.getTransactionById(transactionId)
        }
    }

    fun createTransaction(
        transactionData: TransactionModel,
        productOutData: List<ProductOutModel>
    ) {
        _createTransactionState.value = UiState.Loading
        viewModelScope.launch {
            _createTransactionState.value = transactionRepository.setTransactionById(transactionData, productOutData)
        }
    }

    fun searchTransactionsByTransactionId(
        transactionId: String
    ) {
        _searchTransactionIdState.value = UiState.Loading
        viewModelScope.launch {
            _searchTransactionIdState.value = transactionRepository.searchTransactionsByTransactionId(transactionId)
        }
    }

    fun searchTransactionsByUserName(
        query: String
    ) {
        _searchTransactionNameState.value = UiState.Loading
        viewModelScope.launch {
            _searchTransactionNameState.value = transactionRepository.searchTransactionsByUserName(query)
        }
    }

    fun searchTransactionsByDateRange(
        startDate: Timestamp,
        endDate: Timestamp
    ) {
        _searchTransactionDateRangeState.value = UiState.Loading
        viewModelScope.launch {
            _searchTransactionDateRangeState.value = transactionRepository.searchTransactionsByDateRange(startDate, endDate)
        }
    }
}