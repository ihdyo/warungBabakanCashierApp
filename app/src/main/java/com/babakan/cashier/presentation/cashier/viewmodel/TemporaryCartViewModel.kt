package com.babakan.cashier.presentation.cashier.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TemporaryCartViewModel : ViewModel() {

    private val _temporaryCartProduct = MutableStateFlow<List<Map<String, Int>>>(emptyList())
    val temporaryCartProduct: StateFlow<List<Map<String, Int>>> = _temporaryCartProduct

    private val _temporaryTotalQuantity = MutableStateFlow(0)
    val temporaryTotalQuantity: StateFlow<Int> = _temporaryTotalQuantity

    fun addProductToTemporaryCart(productId: String, quantity: Int) {
        val currentCart = _temporaryCartProduct.value.toMutableList()

        val existingIndex = currentCart.indexOfFirst { it.containsKey(productId) }

        if (existingIndex != -1) {
            val currentQuantity = currentCart[existingIndex][productId] ?: 0
            val updatedQuantity = currentQuantity + quantity

            if (updatedQuantity > 0) {
                currentCart[existingIndex] = mapOf(productId to updatedQuantity)
            } else {
                currentCart.removeAt(existingIndex)
            }
        } else if (quantity > 0) {
            currentCart.add(mapOf(productId to quantity))
        }
        _temporaryCartProduct.value = currentCart
        _temporaryTotalQuantity.value = currentCart.sumOf { it.values.sum() }
    }

    fun clearTemporaryCart() {
        _temporaryCartProduct.value = emptyList()
        _temporaryTotalQuantity.value = 0
    }
}