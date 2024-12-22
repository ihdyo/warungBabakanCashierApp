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

    private val _fetchProductOutState = MutableStateFlow<UiState<List<ProductOutModel>>>(UiState.Idle)
    val fetchProductOutState: StateFlow<UiState<List<ProductOutModel>>> = _fetchProductOutState

    private val _addProductOutState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val addProductOutState: StateFlow<UiState<Unit>> = _addProductOutState

    fun fetchProductOut(
        transactionId: String
    ) {
        _fetchProductOutState.value = UiState.Loading
        viewModelScope.launch {
            _fetchProductOutState.value = productOutRepository.getProductOut(transactionId)
        }
    }

    fun addProductOut(
        transactionId: String,
        productOutData: List<ProductOutModel>
    ) {
        _addProductOutState.value = UiState.Loading
        viewModelScope.launch {
            _addProductOutState.value = productOutRepository.createProductOut(transactionId, productOutData)
        }
    }

}