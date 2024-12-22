package com.babakan.cashier.common.audit.product

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
import com.babakan.cashier.common.audit.product.component.ProductAuditForm
import com.babakan.cashier.common.ui.AuditItemDialog
import com.babakan.cashier.data.dummy.dummyCategoryList
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter
import com.babakan.cashier.utils.validator.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun ProductBottomSheet(
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    sheetState: SheetState,
    item: ProductModel = ProductModel(),
    onDismiss: () -> Unit,
    isAddNew: Boolean = false
) {
    val context = LocalContext.current

    val categories = dummyCategoryList // TODO: get categories
    val category = categories.find { it.id == item.categoryId }

    var showCategories by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf(if (isAddNew) "" else item.name) }
    var selectedCategory by remember { mutableStateOf(if (isAddNew) categories[0] else category) }
    var price by remember { mutableStateOf(if (isAddNew) "" else Formatter.cleanDecimal(item.price)) }
    var imageUrl by remember { mutableStateOf(if (isAddNew) "" else item.imageUrl) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var priceError by remember { mutableStateOf<String?>(null) }
    var imageUrlError by remember { mutableStateOf<String?>(null) }

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
                    text = if (isAddNew) stringResource(R.string.addMenu) else stringResource(R.string.updateMenu),
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
            ProductAuditForm(
                context = context,
                name = name,
                nameError = nameError,
                onNameChange = { name = it },
                showCategories = showCategories,
                categories = categories,
                onCategoryClick = { showCategories = !showCategories },
                selectedCategory = selectedCategory,
                onSelectedCategoryChange = { selectedCategory = it },
                price = price,
                priceError = priceError,
                onPriceChange = { price = it },
                imageUrl = imageUrl,
                imageUrlError = imageUrlError,
                onImageUrlChange = { imageUrl = it },
            )
            Button(
                {
                    nameError = Validator.isNotEmpty(context, name, context.getString(R.string.menu))
                    priceError = Validator.isNotEmpty(context, price, context.getString(R.string.price)) ?: Validator.isValidPrice(context, price)
                    imageUrlError = Validator.isNotEmpty(context, imageUrl, context.getString(R.string.imageUrl)) ?: Validator.isValidUrl(context, imageUrl)

                    if (nameError == null && priceError == null && imageUrlError == null) {
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
                title = stringResource(R.string.updateMenu),
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
                title = stringResource(R.string.deleteMenu),
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