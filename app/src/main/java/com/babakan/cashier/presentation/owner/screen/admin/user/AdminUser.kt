package com.babakan.cashier.presentation.owner.screen.admin.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.common.list.UserList
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.owner.viewmodel.UserViewModel
import com.babakan.cashier.utils.constant.AuditState

@ExperimentalMaterial3Api
@Composable
fun AdminUser(
    userViewModel: UserViewModel = viewModel(),
    nestedScrollConnection: NestedScrollConnection,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (UserModel) -> Unit
) {
    val usersState by userViewModel.fetchUsersState.collectAsState()

    val users = when (val state = usersState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val showLoading = usersState is UiState.Loading

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