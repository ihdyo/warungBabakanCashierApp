package com.babakan.cashier.presentation.owner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.data.repository.productOut.ProductOutRepository
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductOutViewModel(
    private val productOutRepository: ProductOutRepository = ProductOutRepository()
) : ViewModel() {

    private val _fetchProductOutState = MutableStateFlow<UiState<List<Pair<String, List<ProductOutModel>>>>>(UiState.Idle)
    val fetchProductOutState: StateFlow<UiState<List<Pair<String, List<ProductOutModel>>>>> = _fetchProductOutState

    private val _addProductOutState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val addProductOutState: StateFlow<UiState<Unit>> = _addProductOutState

    private val _modifyProductOutState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val modifyProductOutState: StateFlow<UiState<Unit>> = _modifyProductOutState

    private val _removeProductOutState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val removeProductOutState: StateFlow<UiState<Unit>> = _removeProductOutState

    fun fetchProductOut(
        transactionId: List<String>
    ) {
        _fetchProductOutState.value = UiState.Loading
        viewModelScope.launch {
            _fetchProductOutState.value = productOutRepository.getProductOut(transactionId)
        }
    }

    fun addProductOut(
        transactionId: String,
        productOutData: ProductOutModel
    ) {
        _addProductOutState.value = UiState.Loading
        viewModelScope.launch {
            _addProductOutState.value = productOutRepository.createProductOut(transactionId, productOutData)
        }
    }

    fun modifyProductOut(
        transactionId: String,
        productId: String,
        fieldName: String,
        newValue: Any
    ) {
        _modifyProductOutState.value = UiState.Loading
        viewModelScope.launch {
            _modifyProductOutState.value = productOutRepository.updateProductOutByProductId(transactionId, productId, fieldName, newValue)
        }
    }

    fun removeProductOutById(
        transactionId: String,
        productId: String
    ) {
        _removeProductOutState.value = UiState.Loading
        viewModelScope.launch {
            _removeProductOutState.value = productOutRepository.deleteProductOutByProductId(transactionId, productId)
        }
    }
}