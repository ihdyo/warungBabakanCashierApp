package com.babakan.cashier.presentation.owner.screen.admin.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.CategoryList
import com.babakan.cashier.data.dummy.dummyCategoryList

@Composable
fun AdminCategory(nestedScrollConnection: NestedScrollConnection) {

    val categories = dummyCategoryList

    Box(Modifier.fillMaxSize()) {
        CategoryList(
            nestedScrollConnection = nestedScrollConnection,
            categories = categories,
            isAdmin = true
        ) {
            // TODO: onAdminEdit
        }
    }
}