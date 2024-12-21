package com.babakan.cashier.presentation.owner.screen.admin.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.CategoryList
import com.babakan.cashier.data.dummy.dummyCategoryList
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.utils.constant.AuditState

@ExperimentalMaterial3Api
@Composable
fun AdminCategory(
    nestedScrollConnection: NestedScrollConnection,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (CategoryModel) -> Unit
) {

    val categories = dummyCategoryList

    Box(Modifier.fillMaxSize()) {
        CategoryList(
            nestedScrollConnection = nestedScrollConnection,
            categories = categories,
            isAdmin = true
        ) { item ->
            onItemSelected(item)
            onAuditStateChange(AuditState.CATEGORY)
        }
    }
}