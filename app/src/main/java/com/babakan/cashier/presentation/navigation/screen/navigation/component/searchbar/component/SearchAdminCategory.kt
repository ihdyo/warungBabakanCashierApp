package com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.common.list.CategoryList
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.viewmodel.CategoryViewModel
import com.babakan.cashier.utils.constant.AuditState

@Composable
fun SearchAdminCategory(
    categoryViewModel: CategoryViewModel = viewModel(),
    nestedScrollConnection: NestedScrollConnection,
    query: String,
    onResultCountChange: (Int) -> Unit,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (CategoryModel) -> Unit,
    isSearchActive: Boolean
) {
    val searchCategoryState by categoryViewModel.searchCategoriesState.collectAsState()

    LaunchedEffect(query) {
        if (query.isNotBlank()) {
            categoryViewModel.searchCategoriesByName(query)
        }
    }

    val categories = when (val state = searchCategoryState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    if (!isSearchActive) {
        categoryViewModel.resetSearchState()
    }

    onResultCountChange(categories.size)

    CategoryList(
        nestedScrollConnection = nestedScrollConnection,
        categories = categories,
        isAdmin = true
    ) { item ->
        onItemSelected(item)
        onAuditStateChange(AuditState.CATEGORY)
    }

}