package com.babakan.cashier.common.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.babakan.cashier.common.item.UserItem
import com.babakan.cashier.common.style.tabContentPadding
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun UserList(
    nestedScrollConnection: NestedScrollConnection,
    users: List<UserModel>,
    isAdmin: Boolean = false,
    onAdminEdit: (UserModel) -> Unit = {}
) {
    LazyColumn(
        contentPadding = tabContentPadding(),
        verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        itemsIndexed(users) { index, item ->
            UserItem(
                userItem = item,
                isAdmin = isAdmin
            ) { onAdminEdit(item) }
        }
    }
}