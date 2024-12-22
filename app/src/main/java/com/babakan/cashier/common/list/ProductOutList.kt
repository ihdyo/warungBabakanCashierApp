package com.babakan.cashier.common.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.common.component.CategoryComponent
import com.babakan.cashier.common.item.ProductOutItem
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.cashier.viewmodel.CartViewModel
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.presentation.owner.viewmodel.CategoryViewModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductViewModel
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun ProductOutList(
    cartViewModel: CartViewModel = viewModel(),
    productViewModel: ProductViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel(),
    isOnCart: Boolean = false,
    productOutItem: List<ProductOutModel>,
    triggerEvent: (Boolean) -> Unit = {}
) {
    val modifyCartState by cartViewModel.modifyCartState.collectAsState()
    val productsState by productViewModel.fetchProductsState.collectAsState()
    val categoriesState by categoryViewModel.fetchCategoriesState.collectAsState()

    var products by remember { mutableStateOf(emptyList<ProductModel>()) }
    var categories by remember { mutableStateOf(emptyList<CategoryModel>()) }

    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(productsState) {
        if (productsState is UiState.Loading) {
            showLoading = true
        } else if (productsState is UiState.Success) {
            showLoading = false
            products = (productsState as UiState.Success<List<ProductModel>>).data
        }
    }
    LaunchedEffect(categoriesState) {
        if (categoriesState is UiState.Loading) {
            showLoading = true
        } else if (categoriesState is UiState.Success) {
            showLoading = false
            categories = (categoriesState as UiState.Success<List<CategoryModel>>).data
        }
    }
    LaunchedEffect(modifyCartState) {
        when (modifyCartState) {
            is UiState.Success -> {
                triggerEvent(true)
            }
            else -> {}
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        val groupedItems = productOutItem.groupBy { item ->
            val productItem = products.find { it.id == item.productId }
            productItem?.categoryId
        }

        for ((categoryId, groupedProducts) in groupedItems) {
            val categoryItem = categories.find { it.id == categoryId } ?: CategoryModel()

            Spacer(Modifier.weight(1f))
            CategoryComponent(
                iconUrl = categoryItem.iconUrl,
                name = categoryItem.name
            )

            for (productOut in groupedProducts) {
                val productItem = products.find { it.id == productOut.productId } ?: ProductModel()

                ProductOutItem(
                    productViewModel = productViewModel,
                    productItem = productItem,
                    productOutItem = productOut,
                    isOnCart = isOnCart,
                    textValue = productOut.quantity.toString(),
                    onTextChanged = { productOut.quantity = it.toInt() },
                    onSubtract = {
                        cartViewModel.subtractQuantityCartItem(productOut.productId)
                    },
                    onAdd = {
                        cartViewModel.addQuantityCartItem(productOut.productId)
                    },
                    onDeleteItemFromCart = {
                        cartViewModel.subtractQuantityCartItem(productOut.productId)
                    }
                )
            }
        }
    }
}