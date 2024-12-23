package com.babakan.cashier.presentation.cashier.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.common.item.ProductThumbnailItem
import com.babakan.cashier.common.style.pageContentPadding
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.cashier.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.presentation.owner.viewmodel.CategoryViewModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductViewModel
import com.babakan.cashier.utils.constant.SizeChart

@ExperimentalMaterial3Api
@Composable
fun Home(
    productViewModel: ProductViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel(),
    selectedCategory: CategoryModel? = null,
    temporaryCartViewModel: TemporaryCartViewModel,
    nestedScrollConnection: NestedScrollConnection,
    isFabShown: Boolean,
) {
    val productsState by productViewModel.fetchProductsState.collectAsState()
    val productByCategoryState by productViewModel.searchProductByCategoryState.collectAsState()
    val categoriesState by categoryViewModel.fetchCategoriesState.collectAsState()

    LaunchedEffect(selectedCategory) {
        if (selectedCategory != null) {
            productViewModel.searchProductByCategory(selectedCategory.id)
        } else {
            productViewModel.resetSearchState()
            productViewModel.fetchProducts()
        }
    }

    val products = when {
        selectedCategory == null -> (productsState as? UiState.Success)?.data ?: emptyList()
        else -> (productByCategoryState as? UiState.Success)?.data ?: emptyList()
    }

    val categories = when (val state = categoriesState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val showLoading = productsState is UiState.Loading || productByCategoryState is UiState.Loading

    Box(Modifier.fillMaxWidth()) {
        if (showLoading) { LinearProgressIndicator(Modifier.fillMaxWidth()) }
        LazyVerticalGrid(
            GridCells.Fixed(2),
            contentPadding = pageContentPadding(isFabShown),
            verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
            horizontalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
            modifier = Modifier
                .nestedScroll(nestedScrollConnection)
        ) {
            itemsIndexed(products) { _, item ->
                val categoryItem = categories.find { it.id == item.categoryId } ?: CategoryModel()

                ProductThumbnailItem(
                    temporaryCartViewModel = temporaryCartViewModel,
                    productItem = item,
                    categoryItem = categoryItem
                )
            }
        }
    }
}