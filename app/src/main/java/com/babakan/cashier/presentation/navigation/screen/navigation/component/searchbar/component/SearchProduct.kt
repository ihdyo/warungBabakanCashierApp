package com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.ProductList
import com.babakan.cashier.data.dummy.dummyProductList
import com.babakan.cashier.presentation.cashier.viewmodel.TemporaryCartViewModel

@Composable
fun SearchProduct(
    temporaryCartViewModel: TemporaryCartViewModel,
    nestedScrollConnection: NestedScrollConnection,
    query: String,
    onResultCountChange: (Int) -> Unit
) {

    val products = if (query.isNotBlank()) {
        dummyProductList
    } else {
        emptyList()
    }

    onResultCountChange(products.size)

    ProductList(
        temporaryCartViewModel = temporaryCartViewModel,
        nestedScrollConnection = nestedScrollConnection,
        products = products,
        isCountable = true
    )

}