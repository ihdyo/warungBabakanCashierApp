package com.babakan.cashier.data.state

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(
        val title: String,
        val message: String
    ) : UiState<Nothing>()
}