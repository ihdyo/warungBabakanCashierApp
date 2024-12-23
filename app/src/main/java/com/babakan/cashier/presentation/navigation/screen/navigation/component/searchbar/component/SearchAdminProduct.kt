package com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.common.list.ProductList
import com.babakan.cashier.common.ui.FullscreenLoading
import com.babakan.cashier.data.dummy.dummyProductList
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.presentation.cashier.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.presentation.owner.viewmodel.CategoryViewModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductViewModel
import com.babakan.cashier.utils.constant.AuditState

@Composable
fun SearchAdminProduct(
    productViewModel: ProductViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel(),
    temporaryCartViewModel: TemporaryCartViewModel,
    nestedScrollConnection: NestedScrollConnection,
    query: String,
    onResultCountChange: (Int) -> Unit,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (ProductModel) -> Unit,
    isSearchActive: Boolean
) {

    val searchProductByNameState by productViewModel.searchProductByNameState.collectAsState()
    val categoriesState by categoryViewModel.fetchCategoriesState.collectAsState()

    LaunchedEffect(query) {
        if (query.isNotBlank()) {
            productViewModel.searchProductByName(query)
        }
    }

    val products = when (val state = searchProductByNameState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }
    val categories = when (val state = categoriesState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    if (!isSearchActive) {
        productViewModel.resetSearchState()
        categoryViewModel.resetAuditState()
        return
    }

    val showLoading = searchProductByNameState is UiState.Loading

    onResultCountChange(products.size)

    if (showLoading) { FullscreenLoading() }
    ProductList(
        products = products,
        categories = categories,
        temporaryCartViewModel = temporaryCartViewModel,
        nestedScrollConnection = nestedScrollConnection,
        isAdmin = true
    ) { item ->
        onItemSelected(item)
        onAuditStateChange(AuditState.PRODUCT)
    }

}