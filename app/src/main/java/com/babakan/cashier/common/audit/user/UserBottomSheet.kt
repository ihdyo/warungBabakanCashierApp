package com.babakan.cashier.common.audit.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.common.audit.user.component.UserAuditForm
import com.babakan.cashier.common.ui.AuditItemDialog
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.validator.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun UserBottomSheet(
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    sheetState: SheetState,
    item: UserModel,
    onDismiss: () -> Unit,
    isAddNew: Boolean = false,
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf(if (isAddNew) "" else item.name) }
    var username by remember { mutableStateOf(if (isAddNew) "" else item.username) }
    var email by remember { mutableStateOf(if (isAddNew) "" else item.email) }
    var isActive by remember { mutableStateOf(if (isAddNew) true else item.isActive) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    var showUpdateDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val onUpdateItem = { showUpdateDialog = true }
    val onDeleteItem = { showDeleteDialog = true }

    // TODO: Implement this audit
    val onAddNewItem = {
        // TODO: Add New
        // if success
        onDismiss()
        scope.launch { snackBarHostState.showSnackbar(context.getString(R.string.addSuccess, name)) }
        // if failed
        scope.launch { snackBarHostState.showSnackbar(context.getString(R.string.addFailed, name)) }
    }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        dragHandle = null
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_SECTIONS.dp),
            modifier = Modifier
                .padding(SizeChart.DEFAULT_SPACE.dp)
                .padding(vertical = if (isAddNew) SizeChart.SMALL_SPACE.dp else SizeChart.SIZE_0.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isAddNew) stringResource(R.string.addUser) else stringResource(R.string.updateUser),
                    style = MaterialTheme.typography.titleLarge
                )
                if (!isAddNew) {
                    IconButton({ onDeleteItem() }) {
                        Icon(
                            Icons.Default.Delete,
                            stringResource(R.string.delete),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            UserAuditForm(
                name = name,
                nameError = nameError,
                onNameChange = { name = it },
                username = username,
                usernameError = usernameError,
                onUsernameChange = { username = it },
                email = email,
                emailError = emailError,
                onEmailChange = { email = it },
                isActive = isActive,
                onIsActiveChange = { isActive = it }
            )
            Button(
                {
                    nameError = Validator.isNotEmpty(context, name, context.getString(R.string.name))
                    usernameError = Validator.isNotEmpty(context, username, context.getString(R.string.username)) ?: Validator.isValidUsername(context, username)
                    emailError = Validator.isNotEmpty(context, email, context.getString(R.string.email)) ?: Validator.isValidEmail(context, email)

                    if (nameError == null && usernameError == null && emailError == null) {
                        if (isAddNew) onAddNewItem() else onUpdateItem()
                    }
                },
                Modifier.fillMaxWidth(),
            ) {
                Text(if (isAddNew) stringResource(R.string.add) else stringResource(R.string.update))
            }
        }
        if (showUpdateDialog) {
            AuditItemDialog(
                title = stringResource(R.string.updateUser),
                body = stringResource(R.string.updateConfirmation, name),
                isDelete = false,
                onDismiss = { showUpdateDialog = false },
                onConfirm = {
                    // TODO: Update
                    // if success
                    onDismiss()
                    showUpdateDialog = false
                    scope.launch { snackBarHostState.showSnackbar(context.getString(R.string.updateSuccess, name)) }
                    // if failed
                    showUpdateDialog = false
                    scope.launch { snackBarHostState.showSnackbar(context.getString(R.string.updateFailed, name)) }
                }
            )
        }
        if (showDeleteDialog) {
            AuditItemDialog(
                title = stringResource(R.string.deleteUser),
                body = stringResource(R.string.deleteConfirmation, name),
                isDelete = true,
                onConfirm = {
                    // TODO: Delete
                    // if success
                    onDismiss()
                    showDeleteDialog = false
                    scope.launch { snackBarHostState.showSnackbar(context.getString(R.string.deleteSuccess, name)) }
                    // if failed
                    showDeleteDialog = false
                    scope.launch { snackBarHostState.showSnackbar(context.getString(R.string.deleteFailed, name)) }
                },
                onDismiss = { showDeleteDialog = false },
            )
        }
    }
}