package com.babakan.cashier.common.audit.category.component

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.babakan.cashier.utils.builder.IconLoader
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.validator.Validator

@Composable
fun CategoryAuditForm(
    context: Context,
    name: String,
    nameError: String?,
    onNameChange: (String) -> Unit,
    iconUrl: String,
    iconUrlError: String?,
    onIconUrlChange: (String) -> Unit
) {

    var iconPreview by remember { mutableStateOf(false) }

    LaunchedEffect(iconUrl) {
        iconPreview = Validator.isValidUrl(context, iconUrl) == null
    }

    Column {
        OutlinedTextField(
            isError = nameError != null,
            value = name,
            onValueChange = { onNameChange(it) },
            label = { Text(stringResource(R.string.category)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = { Icon(Icons.Outlined.Category, stringResource(R.string.category)) },
            supportingText = { nameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
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
                isError = iconUrlError != null,
                value = iconUrl,
                onValueChange = { onIconUrlChange(it) },
                label = { Text(stringResource(R.string.iconUrl)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = { Icon(Icons.Outlined.Link, stringResource(R.string.iconUrl)) },
                trailingIcon = {
                    IconButton(
                        {
                            val clipboard = context.getSystemService(ClipboardManager::class.java)
                            val clip = clipboard.primaryClip
                            if (clip != null && clip.itemCount > 0) {
                                onIconUrlChange(clip.getItemAt(0).text.toString())
                            }
                        },
                    ) {
                        Icon(
                            Icons.Outlined.ContentPaste,
                            stringResource(R.string.paste)
                        )
                    }
                },
                supportingText = { iconUrlError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                    errorLeadingIconColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.weight(1f)
            )
            AnimatedVisibility(iconPreview) {
                Card(
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    modifier = Modifier.padding(top = SizeChart.SIZE_SM.dp)
                ) {
                    Box(
                        modifier = Modifier.padding(SizeChart.SIZE_LG.dp)
                    ) {
                        IconLoader(
                            iconUrl = iconUrl,
                            size = SizeChart.ICON_EXTRA_LARGE.dp
                        )
                    }
                }
            }
        }
    }
}