package com.babakan.cashier.presentation.owner.screen.admin.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.ProductList
import com.babakan.cashier.data.dummy.dummyProductList
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.utils.constant.AuditState

@ExperimentalMaterial3Api
@Composable
fun AdminProduct(
    products: List<ProductModel>,
    categories: List<CategoryModel>,
    nestedScrollConnection: NestedScrollConnection,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (ProductModel) -> Unit,
    showLoading: Boolean
) {
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