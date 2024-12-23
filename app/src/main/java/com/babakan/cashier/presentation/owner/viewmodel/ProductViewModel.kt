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

    private val _searchProductByNameState = MutableStateFlow<UiState<List<ProductModel>>>(UiState.Idle)
    val searchProductByNameState: StateFlow<UiState<List<ProductModel>>> = _searchProductByNameState

    private val _searchProductByCategoryState = MutableStateFlow<UiState<List<ProductModel>>>(UiState.Idle)
    val searchProductByCategoryState: StateFlow<UiState<List<ProductModel>>> = _searchProductByCategoryState

    init {
        fetchProducts()
    }

    fun resetAuditState() {
        _createProductState.value = UiState.Idle
        _updateProductState.value = UiState.Idle
        _deleteProductState.value = UiState.Idle
    }

    fun resetSearchState() {
        _searchProductByNameState.value = UiState.Idle
        _searchProductByCategoryState.value = UiState.Idle
    }

    fun fetchProducts() {
        _fetchProductsState.value = UiState.Loading
        viewModelScope.launch {
            _fetchProductsState.value = productRepository.getProducts()
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

    fun updateProduct(
        productId: String,
        productData: ProductModel
    ) {
        _updateProductState.value = UiState.Loading
        viewModelScope.launch {
            _updateProductState.value = productRepository.updateProduct(productId, productData)
        }
    }

    fun deleteProduct(
        productId: String
    ) {
        _deleteProductState.value = UiState.Loading
        viewModelScope.launch {
            _deleteProductState.value = productRepository.deleteProduct(productId)
        }
    }

    fun searchProductByName(
        query: String
    ) {
        _searchProductByNameState.value = UiState.Loading
        viewModelScope.launch {
            _searchProductByNameState.value = productRepository.searchProductsByName(query)
        }
    }

    fun searchProductByCategory(
        categoryId: String
    ) {
        _searchProductByCategoryState.value = UiState.Loading
        viewModelScope.launch {
            _searchProductByCategoryState.value = productRepository.searchProductsByCategoryId(categoryId)
        }
    }
}