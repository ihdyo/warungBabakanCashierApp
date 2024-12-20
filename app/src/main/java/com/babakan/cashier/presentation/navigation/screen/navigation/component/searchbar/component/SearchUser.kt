package com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.UserList
import com.babakan.cashier.data.dummy.dummyUserList

@Composable
fun SearchUser(
    nestedScrollConnection: NestedScrollConnection,
    query: String,
    onResultCountChange: (Int) -> Unit
) {

    val users = if (query.isNotBlank()) {
        dummyUserList
    } else {
        emptyList()
    }
    onResultCountChange(users.size)

    UserList(
        nestedScrollConnection = nestedScrollConnection,
        users = users
    )

}