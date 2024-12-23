package com.babakan.cashier.presentation.owner.screen.admin.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.common.list.ProductList
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.presentation.owner.viewmodel.CategoryViewModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductViewModel
import com.babakan.cashier.utils.constant.AuditState

@ExperimentalMaterial3Api
@Composable
fun AdminProduct(
    productViewModel: ProductViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel(),
    nestedScrollConnection: NestedScrollConnection,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (ProductModel) -> Unit
) {
    val productsState by productViewModel.fetchProductsState.collectAsState()
    val categoriesState by categoryViewModel.fetchCategoriesState.collectAsState()


    val products = when (val state = productsState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val categories = when (val state = categoriesState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val showLoading = productsState is UiState.Loading || categoriesState is UiState.Loading

    Box(Modifier.fillMaxHeight()) {
        if (showLoading) { LinearProgressIndicator(Modifier.fillMaxWidth()) }
        ProductList(
            products = products,
            categories = categories,
            nestedScrollConnection = nestedScrollConnection,
            isAdmin = true
        ) { item ->
            onItemSelected(item)
            onAuditStateChange(AuditState.PRODUCT)
        }
    }
}