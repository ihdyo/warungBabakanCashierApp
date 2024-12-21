package com.babakan.cashier.common.audit.product.component

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.common.ui.ShowImageDialog
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.utils.builder.IconLoader
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.validator.Validator

@ExperimentalMaterial3Api
@Composable
fun ProductAuditForm(
    context: Context,
    name: String,
    nameError: String?,
    onNameChange: (String) -> Unit,
    showCategories: Boolean,
    categories: List<CategoryModel>,
    onCategoryClick: (Boolean) -> Unit,
    selectedCategory: CategoryModel?,
    onSelectedCategoryChange: (CategoryModel) -> Unit,
    price: String,
    priceError: String?,
    onPriceChange: (String) -> Unit,
    imageUrl: String,
    imageUrlError: String?,
    onImageUrlChange: (String) -> Unit
) {
    var imagePreviewButton by remember { mutableStateOf(false) }
    var showImagePreview by remember { mutableStateOf(false) }

    LaunchedEffect(imageUrl) {
        imagePreviewButton = Validator.isValidUrl(context, imageUrl) == null
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(SizeChart.DEFAULT_SPACE.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                isError = nameError != null,
                value = name,
                onValueChange = { onNameChange(it) },
                label = { Text(stringResource(R.string.menu)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = { Icon(Icons.Outlined.RestaurantMenu, stringResource(R.string.menu)) },
                supportingText = { nameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                    errorLeadingIconColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            ExposedDropdownMenuBox(
                expanded = showCategories,
                onExpandedChange = { onCategoryClick(!it) },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = selectedCategory!!.name,
                    onValueChange = {},
                    label = { Text(stringResource(R.string.category)) },
                    singleLine = true,
                    readOnly = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    leadingIcon = {
                        IconLoader(
                            selectedCategory.iconUrl,
                            SizeChart.ICON_LARGE.dp,
                            MaterialTheme.colorScheme.onSurface
                        )
                    },
                    trailingIcon = {
                        Icon(
                            Icons.Outlined.ArrowDropDown,
                            stringResource(R.string.info)
                        )
                    },
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                        errorLeadingIconColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = showCategories,
                    onDismissRequest = { onCategoryClick(false) },
                ) {
                    categories.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                onSelectedCategoryChange(item)
                                onCategoryClick(false)
                            },
                            text = {
                                Text(
                                    item.name,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            leadingIcon = {
                                IconLoader(
                                    item.iconUrl,
                                    SizeChart.ICON_LARGE.dp,
                                    MaterialTheme.colorScheme.onSurface
                                )
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }
        OutlinedTextField(
            isError = priceError != null,
            value = price,
            onValueChange = { onPriceChange(it) },
            label = { Text(stringResource(R.string.price)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            prefix = { Text(stringResource(R.string.currencyPrefix)) },
            suffix = { Text(stringResource(R.string.currencySuffix)) },
            leadingIcon = { Icon(Icons.Outlined.Sell, stringResource(R.string.price)) },
            supportingText = { priceError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                errorLeadingIconColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(SizeChart.DEFAULT_SPACE.dp)
        ) {
            OutlinedTextField(
                isError = imageUrlError != null,
                value = imageUrl,
                onValueChange = { onImageUrlChange(it) },
                label = { Text(stringResource(R.string.imageUrl)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = { Icon(Icons.Outlined.Link, stringResource(R.string.imageUrl)) },
                trailingIcon = {
                    IconButton(
                        {
                            val clipboard = context.getSystemService(ClipboardManager::class.java)
                            val clip = clipboard.primaryClip
                            if (clip != null && clip.itemCount > 0) {
                                onImageUrlChange(clip.getItemAt(0).text.toString())
                            }
                        },
                    ) {
                        Icon(
                            Icons.Outlined.ContentPaste,
                            stringResource(R.string.paste)
                        )
                    }
                },
                supportingText = { imageUrlError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                    errorLeadingIconColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.weight(1f)
            )
            AnimatedVisibility(imagePreviewButton) {
                Card(
                    { showImagePreview = !showImagePreview },
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    modifier = Modifier.padding(top = SizeChart.SIZE_SM.dp)
                ) {
                    Icon(
                        Icons.Default.Image,
                        stringResource(R.string.imageUrl),
                        modifier = Modifier.padding(SizeChart.SIZE_LG.dp)
                    )
                }
            }
        }
    }
    if (showImagePreview) ShowImageDialog(imageUrl) { showImagePreview = !showImagePreview }
}