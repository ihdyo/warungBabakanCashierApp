package com.babakan.cashier.presentation.owner.screen.admin.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
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
    users: List<UserModel>,
    nestedScrollConnection: NestedScrollConnection,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (UserModel) -> Unit,
    showLoading: Boolean
) {
    Box(Modifier.fillMaxSize()) {
        if (showLoading) { LinearProgressIndicator(Modifier.fillMaxWidth()) }
        UserList(
            users = users,
            nestedScrollConnection = nestedScrollConnection,
            isAdmin = true
        ) { item ->
            onItemSelected(item)
            onAuditStateChange(AuditState.USER)
        }
    }
}