package com.babakan.cashier.presentation.owner.screen.admin.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.ProductList
import com.babakan.cashier.data.dummy.dummyProductList

@Composable
fun AdminProduct(
    nestedScrollConnection: NestedScrollConnection
) {

    val products = dummyProductList

    Box(Modifier.fillMaxHeight()) {
        ProductList(
            nestedScrollConnection = nestedScrollConnection,
            products = products,
            isAdmin = true
        ) {
            // TODO: onAdminEdit
        }
    }
}