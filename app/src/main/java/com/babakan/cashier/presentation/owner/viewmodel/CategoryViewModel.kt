package com.babakan.cashier.presentation.owner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.data.repository.category.CategoryRepository
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.CategoryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: CategoryRepository = CategoryRepository()
) : ViewModel() {

    private val _fetchCategoriesState = MutableStateFlow<UiState<List<CategoryModel>>>(UiState.Idle)
    val fetchCategoriesState: StateFlow<UiState<List<CategoryModel>>> = _fetchCategoriesState

    private val _fetchCategoryByIdState = MutableStateFlow<UiState<CategoryModel>>(UiState.Idle)
    val fetchCategoryByIdState: StateFlow<UiState<CategoryModel>> = _fetchCategoryByIdState

    private val _createCategoryState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val createCategoryState: StateFlow<UiState<Unit>> = _createCategoryState

    private val _updateCategoryState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val updateCategoryState: StateFlow<UiState<Unit>> = _updateCategoryState

    private val _deleteCategoryState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val deleteCategoryState: StateFlow<UiState<Unit>> = _deleteCategoryState

    fun fetchCategories() {
        _fetchCategoriesState.value = UiState.Loading
        viewModelScope.launch {
            _fetchCategoriesState.value = categoryRepository.getCategories()
        }
    }

    fun fetchCategoryById(
        categoryId: String
    ) {
        _fetchCategoryByIdState.value = UiState.Loading
        viewModelScope.launch {
            _fetchCategoryByIdState.value = categoryRepository.getCategoryById(categoryId)
        }
    }

    fun createCategory(
        categoryData: CategoryModel
    ) {
        _createCategoryState.value = UiState.Loading
        viewModelScope.launch {
            _createCategoryState.value = categoryRepository.createCategory(categoryData)
        }
    }

    fun updateCategoryById(
        categoryId: String,
        fieldName: String,
        newValue: Any
    ) {
        _updateCategoryState.value = UiState.Loading
        viewModelScope.launch {
            _updateCategoryState.value = categoryRepository.updateCategoryById(categoryId, fieldName, newValue)
        }
    }

    fun deleteCategoryById(
        categoryId: String
    ) {
        _deleteCategoryState.value = UiState.Loading
        viewModelScope.launch {
            _deleteCategoryState.value = categoryRepository.deleteCategoryById(categoryId)
        }
    }
}