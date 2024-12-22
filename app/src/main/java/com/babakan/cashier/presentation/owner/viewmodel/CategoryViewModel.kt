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

    private val _createCategoryState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val createCategoryState: StateFlow<UiState<Unit>> = _createCategoryState

    private val _updateCategoryState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val updateCategoryState: StateFlow<UiState<Unit>> = _updateCategoryState

    private val _deleteCategoryState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val deleteCategoryState: StateFlow<UiState<Unit>> = _deleteCategoryState

    private val _searchCategoriesState = MutableStateFlow<UiState<List<CategoryModel>>>(UiState.Idle)
    val searchCategoriesState: StateFlow<UiState<List<CategoryModel>>> = _searchCategoriesState

    init {
        fetchCategories()
    }

    fun resetAuditState() {
        _createCategoryState.value = UiState.Idle
        _updateCategoryState.value = UiState.Idle
        _deleteCategoryState.value = UiState.Idle
    }

    fun resetSearchState() {
        _searchCategoriesState.value = UiState.Idle
    }

    fun fetchCategories() {
        _fetchCategoriesState.value = UiState.Loading
        viewModelScope.launch {
            _fetchCategoriesState.value = categoryRepository.getCategories()
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

    fun updateCategory(
        categoryId: String,
        categoryData: CategoryModel
    ) {
        _updateCategoryState.value = UiState.Loading
        viewModelScope.launch {
            _updateCategoryState.value = categoryRepository.updateCategory(categoryId, categoryData)
        }
    }

    fun deleteCategory(
        categoryId: String
    ) {
        _deleteCategoryState.value = UiState.Loading
        viewModelScope.launch {
            _deleteCategoryState.value = categoryRepository.deleteCategory(categoryId)
        }
    }

    fun searchCategoriesByName(
        query: String
    ) {
        _searchCategoriesState.value = UiState.Loading
        viewModelScope.launch {
            _searchCategoriesState.value = categoryRepository.searchCategoriesByName(query)
        }
    }
}