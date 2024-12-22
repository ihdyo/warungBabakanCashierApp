package com.babakan.cashier.presentation.cashier.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.data.repository.cart.CartRepository
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository = CartRepository()
) : ViewModel() {

    private val _fetchCartState = MutableStateFlow<UiState<List<ProductOutModel>>>(UiState.Idle)
    val fetchCartState: StateFlow<UiState<List<ProductOutModel>>> = _fetchCartState

    private val _addCartState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val addCartState: StateFlow<UiState<Unit>> = _addCartState

    private val _modifyCartState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val modifyCartState: StateFlow<UiState<Unit>> = _modifyCartState

    private val _clearCartState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val clearCartState: StateFlow<UiState<Unit>> = _clearCartState

    init {
        fetchCart()
    }

    fun fetchCart() {
        _fetchCartState.value = UiState.Loading
        viewModelScope.launch {
            _fetchCartState.value = cartRepository.getCart()
        }
    }

    fun addCart(
        listCartData: List<ProductOutModel>
    ) {
        _addCartState.value = UiState.Loading
        viewModelScope.launch {
            _addCartState.value = cartRepository.createCart(listCartData)
        }
    }

    fun addQuantityCartItem(
        productId: String
    ) {
        _modifyCartState.value = UiState.Loading
        viewModelScope.launch {
            _modifyCartState.value = cartRepository.addQuantity(productId)
        }
    }

    fun setPriceCartItem(
        productId: String,
        price: Double
    ) {
        _modifyCartState.value = UiState.Loading
        viewModelScope.launch {
            _modifyCartState.value = cartRepository.setPrice(productId, price)
        }
    }

    fun subtractQuantityCartItem(
        productId: String
    ) {
        _modifyCartState.value = UiState.Loading
        viewModelScope.launch {
            _modifyCartState.value = cartRepository.subtractQuantity(productId)
        }
    }

    fun clearCart() {
        _clearCartState.value = UiState.Loading
        viewModelScope.launch {
            _clearCartState.value = cartRepository.clearCart()
        }
    }
}