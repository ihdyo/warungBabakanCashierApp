package com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.UserList
import com.babakan.cashier.data.dummy.dummyUserList
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.utils.constant.AuditState

@Composable
fun SearchAdminUser(
    nestedScrollConnection: NestedScrollConnection,
    query: String,
    onResultCountChange: (Int) -> Unit,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (UserModel) -> Unit
) {

    val users = if (query.isNotBlank()) {
        dummyUserList
    } else {
        emptyList()
    }
    onResultCountChange(users.size)

    UserList(
        nestedScrollConnection = nestedScrollConnection,
        users = users,
        isAdmin = true
    ) { item ->
        onItemSelected(item)
        onAuditStateChange(AuditState.USER)
    }

}