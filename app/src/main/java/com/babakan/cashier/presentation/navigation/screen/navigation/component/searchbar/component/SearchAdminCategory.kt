package com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.CategoryList
import com.babakan.cashier.data.dummy.dummyCategoryList
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.utils.constant.AuditState

@Composable
fun SearchAdminCategory(
    nestedScrollConnection: NestedScrollConnection,
    query: String,
    onResultCountChange: (Int) -> Unit,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (CategoryModel) -> Unit
) {

    val categories = if (query.isNotBlank()) {
        dummyCategoryList
    } else {
        emptyList()
    }
    onResultCountChange(categories.size)

    CategoryList(
        nestedScrollConnection = nestedScrollConnection,
        categories = categories,
        isAdmin = true
    ) { item ->
        onItemSelected(item)
        onAuditStateChange(AuditState.CATEGORY)
    }

}