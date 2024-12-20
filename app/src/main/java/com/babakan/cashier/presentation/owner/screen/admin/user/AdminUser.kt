package com.babakan.cashier.presentation.owner.screen.admin.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import com.babakan.cashier.common.list.UserList
import com.babakan.cashier.data.dummy.dummyUserList

@Composable
fun AdminUser(nestedScrollConnection: NestedScrollConnection) {

    val cashier = dummyUserList

    Box(Modifier.fillMaxSize()) {
        UserList(
            nestedScrollConnection = nestedScrollConnection,
            users = cashier,
            isAdmin = true
        ) {
            // TODO: onAdminEdit
        }
    }
}