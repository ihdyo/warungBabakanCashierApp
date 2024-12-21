package com.babakan.cashier.presentation.owner.screen.admin.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.UserList
import com.babakan.cashier.data.dummy.dummyUserList
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.utils.constant.AuditState

@ExperimentalMaterial3Api
@Composable
fun AdminUser(
    nestedScrollConnection: NestedScrollConnection,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (UserModel) -> Unit
) {

    val cashier = dummyUserList

    Box(Modifier.fillMaxSize()) {
        UserList(
            nestedScrollConnection = nestedScrollConnection,
            users = cashier,
            isAdmin = true
        ) { item ->
            onItemSelected(item)
            onAuditStateChange(AuditState.USER)
        }
    }
}