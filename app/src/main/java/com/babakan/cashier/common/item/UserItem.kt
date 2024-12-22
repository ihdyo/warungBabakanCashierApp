package com.babakan.cashier.common.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.R
import com.babakan.cashier.common.component.EditButton
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.authentication.viewmodel.AuthViewModel
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun UserItem(
    authViewModel: AuthViewModel = viewModel(),
    index: Int,
    userItem: UserModel,
    isAdmin: Boolean = false,
    onAdminEdit: (UserModel) -> Unit = {}
) {
    val currentUserState by authViewModel.currentUserState.collectAsState()

    val isCurrentUser = currentUserState is UiState.Success && userItem.id == (currentUserState as UiState.Success<UserModel>).data.id

    val isActive = userItem.isActive
    val isOwner = userItem.isOwner

    Card(colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
            modifier = Modifier.padding(SizeChart.SMALL_SPACE.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    userItem.username,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (isAdmin && (isCurrentUser || !isOwner)) { EditButton { onAdminEdit(userItem) } }
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        userItem.name,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        userItem.email,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(SizeChart.SIZE_2XS.dp)
                    ) {
                        if (isOwner) {
                            Icon(
                                Icons.Default.Verified,
                                stringResource(R.string.owner),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(SizeChart.ICON_LARGE.dp)
                            )
                        }
                        Text(
                            if (isOwner) stringResource(R.string.owner) else stringResource(R.string.cashier),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Card(
                        shape = MaterialTheme.shapes.extraSmall,
                        colors = CardDefaults.cardColors(if (isActive) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Icon(
                            if (isActive) Icons.Default.Check else Icons.Default.Close,
                            stringResource(R.string.status),
                            tint = if (isActive) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier
                                .padding(SizeChart.SIZE_2XS.dp)
                                .size(SizeChart.ICON_EXTRA_SMALL.dp)
                        )
                    }
                }
            }
        }
    }
}