package com.babakan.cashier.presentation.owner.screen.admin.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.common.list.CategoryList
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.viewmodel.CategoryViewModel
import com.babakan.cashier.utils.constant.AuditState

@ExperimentalMaterial3Api
@Composable
fun AdminCategory(
    categoryViewModel: CategoryViewModel = viewModel(),
    nestedScrollConnection: NestedScrollConnection,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (CategoryModel) -> Unit
) {
    val categoriesState by categoryViewModel.fetchCategoriesState.collectAsState()

    val categories = when (val state = categoriesState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val showLoading = categoriesState is UiState.Loading

    Box(Modifier.fillMaxSize()) {
        if (showLoading) { LinearProgressIndicator(Modifier.fillMaxWidth()) }
        CategoryList(
            categories = categories,
            nestedScrollConnection = nestedScrollConnection,
            isAdmin = true
        ) { item ->
            onItemSelected(item)
            onAuditStateChange(AuditState.CATEGORY)
        }
    }
}