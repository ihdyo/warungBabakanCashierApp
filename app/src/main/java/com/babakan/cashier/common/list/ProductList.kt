package com.babakan.cashier.common.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.common.item.ProductItem
import com.babakan.cashier.common.style.tabContentPadding
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.presentation.cashier.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun ProductList(
    products: List<ProductModel>,
    categories: List<CategoryModel>,
    temporaryCartViewModel: TemporaryCartViewModel = viewModel(),
    nestedScrollConnection: NestedScrollConnection,
    isAdmin: Boolean = false,
    isCountable: Boolean = false,
    onAdminEdit: (ProductModel) -> Unit = {}
) {
    LazyColumn(
        contentPadding = tabContentPadding(),
        verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        itemsIndexed(products) { index, item ->
            val categoryItem = categories.find { it.id == item.categoryId } ?: CategoryModel()
            ProductItem(
                temporaryCartViewModel = temporaryCartViewModel,
                productItem = item,
                categoryItem = categoryItem,
                isCountable = isCountable,
                isAdmin = isAdmin
            ) { onAdminEdit(item) }
        }
    }
}