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

    private val _removeCartState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val removeCartState: StateFlow<UiState<Unit>> = _removeCartState

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
        cartData: ProductOutModel
    ) {
        _addCartState.value = UiState.Loading
        viewModelScope.launch {
            _addCartState.value = cartRepository.createCart(cartData)
        }
    }

    fun modifyCart(
        productId: String,
        fieldName: String,
        newValue: Any
    ) {
        _modifyCartState.value = UiState.Loading
        viewModelScope.launch {
            _modifyCartState.value = cartRepository.updateCartByProductId(productId, fieldName, newValue)
        }
    }

    fun removeCartById(
        productId: String
    ) {
        _removeCartState.value = UiState.Loading
        viewModelScope.launch {
            _removeCartState.value = cartRepository.deleteCartByProductId(productId)
        }
    }
}