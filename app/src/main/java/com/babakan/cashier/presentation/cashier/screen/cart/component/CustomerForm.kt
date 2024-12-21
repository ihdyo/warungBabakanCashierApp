package com.babakan.cashier.presentation.cashier.screen.cart.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Notes
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun CustomerForm(
    customerName: String,
    customerNameError: String?,
    tableNumber: String,
    tableNumberError: String?,
    notes: String,
    onCustomerNameChange: (String) -> Unit,
    onTableNumberChange: (String) -> Unit,
    onCustomerNoteChange: (String) -> Unit,
    onExpandClick: () -> Unit
) {
    Column {
        Text(
            stringResource(R.string.customerData),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = SizeChart.BETWEEN_ITEMS.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(SizeChart.DEFAULT_SPACE.dp),) {
            OutlinedTextField(
                isError = customerNameError != null,
                value = customerName,
                onValueChange = { onCustomerNameChange(it) },
                label = { Text(stringResource(R.string.customerName)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = { Icon(Icons.Outlined.SentimentVerySatisfied, stringResource(R.string.customerName)) },
                supportingText = { customerNameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                    errorLeadingIconColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.weight(3f),
            )
            OutlinedTextField(
                isError = tableNumberError != null,
                value = tableNumber,
                onValueChange = { onTableNumberChange(it) },
                label = { Text(stringResource(R.string.number)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Icon(Icons.Outlined.ConfirmationNumber, stringResource(R.string.tableNumber)) },
                supportingText = { tableNumberError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                    errorLeadingIconColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.weight(2f)
            )
        }
        OutlinedTextField(
            value = notes,
            onValueChange = { onCustomerNoteChange(it) },
            label = { Text(stringResource(R.string.notes)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = { Icon(Icons.AutoMirrored.Outlined.Notes, stringResource(R.string.notes)) },
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                errorLeadingIconColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = SizeChart.SMALL_SPACE.dp)
        )
        AnimatedVisibility(
            tableNumber.isNotBlank() && customerName.isNotBlank(),
            Modifier.align(Alignment.CenterHorizontally)
        ) {
            IconButton({ onExpandClick() }) {
                Icon(
                    Icons.Default.ExpandLess,
                    stringResource(R.string.collapse),
                )
            }
        }
    }
}