package com.babakan.cashier.presentation.owner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.data.repository.product.ProductRepository
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.ProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository = ProductRepository()
) : ViewModel() {

    private val _fetchProductsState = MutableStateFlow<UiState<List<ProductModel>>>(UiState.Idle)
    val fetchProductsState: StateFlow<UiState<List<ProductModel>>> = _fetchProductsState

    private val _fetchProductByIdState = MutableStateFlow<UiState<ProductModel>>(UiState.Idle)
    val fetchProductByIdState: StateFlow<UiState<ProductModel>> = _fetchProductByIdState

    private val _createProductState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val createProductState: StateFlow<UiState<Unit>> = _createProductState

    private val _updateProductState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val updateProductState: StateFlow<UiState<Unit>> = _updateProductState

    private val _deleteProductState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val deleteProductState: StateFlow<UiState<Unit>> = _deleteProductState

    fun fetchProducts() {
        _fetchProductsState.value = UiState.Loading
        viewModelScope.launch {
            _fetchProductsState.value = productRepository.getProducts()
        }
    }

    fun fetchProductById(
        productId: String
    ) {
        _fetchProductByIdState.value = UiState.Loading
        viewModelScope.launch {
            _fetchProductByIdState.value = productRepository.getProductById(productId)
        }
    }

    fun createProduct(
        productData: ProductModel
    ) {
        _createProductState.value = UiState.Loading
        viewModelScope.launch {
            _createProductState.value = productRepository.createProduct(productData)
        }
    }

    fun updateProductById(
        productId: String,
        fieldName: String,
        newValue: Any
    ) {
        _updateProductState.value = UiState.Loading
        viewModelScope.launch {
            _updateProductState.value = productRepository.updateProductById(productId, fieldName, newValue)
        }
    }

    fun deleteProductById(
        productId: String
    ) {
        _deleteProductState.value = UiState.Loading
        viewModelScope.launch {
            _deleteProductState.value = productRepository.deleteProductById(productId)
        }
    }
}