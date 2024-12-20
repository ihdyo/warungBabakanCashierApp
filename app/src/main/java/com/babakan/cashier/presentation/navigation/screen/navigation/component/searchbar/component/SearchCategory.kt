package com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.CategoryList
import com.babakan.cashier.data.dummy.dummyCategoryList

@Composable
fun SearchCategory(
    nestedScrollConnection: NestedScrollConnection,
    query: String,
    onResultCountChange: (Int) -> Unit
) {

    val categories = if (query.isNotBlank()) {
        dummyCategoryList
    } else {
        emptyList()
    }
    onResultCountChange(categories.size)

    CategoryList(
        nestedScrollConnection = nestedScrollConnection,
        categories = categories
    )

}