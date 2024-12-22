package com.babakan.cashier.presentation.owner.screen.admin.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.CategoryList
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.utils.constant.AuditState

@ExperimentalMaterial3Api
@Composable
fun AdminCategory(
    categories: List<CategoryModel>,
    nestedScrollConnection: NestedScrollConnection,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (CategoryModel) -> Unit,
    showLoading: Boolean
) {
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