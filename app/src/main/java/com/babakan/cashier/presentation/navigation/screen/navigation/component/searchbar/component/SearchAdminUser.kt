package com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.common.list.UserList
import com.babakan.cashier.common.ui.FullscreenLoading
import com.babakan.cashier.data.dummy.dummyUserList
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.owner.viewmodel.UserViewModel
import com.babakan.cashier.utils.constant.AuditState

@Composable
fun SearchAdminUser(
    userViewModel: UserViewModel = viewModel(),
    nestedScrollConnection: NestedScrollConnection,
    query: String,
    onResultCountChange: (Int) -> Unit,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (UserModel) -> Unit,
    isSearchActive: Boolean
) {
    val searchUserState by userViewModel.searchUserByName.collectAsState()

    LaunchedEffect(query) {
        if (query.isNotBlank()) {
            userViewModel.searchUsersByName(query)
        }
    }

    val users = when (val state = searchUserState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    if (!isSearchActive) {
        userViewModel.resetSearchState()
        return
    }

    val showLoading = searchUserState is UiState.Loading

    onResultCountChange(users.size)

    if (showLoading) { FullscreenLoading() }
    UserList(
        nestedScrollConnection = nestedScrollConnection,
        users = users,
        isAdmin = true
    ) { item ->
        onItemSelected(item)
        onAuditStateChange(AuditState.USER)
    }

}