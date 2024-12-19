package com.babakan.cashier.presentation.cashier.screen.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.outlined.Notes
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.babakan.cashier.R
import com.babakan.cashier.common.component.EditButton
import com.babakan.cashier.common.list.ProductOutList
import com.babakan.cashier.data.dummy.dummyProductOutList
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter
import com.babakan.cashier.utils.validator.Validator
import kotlinx.coroutines.launch

@Composable
fun Cart(
    nestedScrollConnection: NestedScrollConnection,
    snackBarHostState: SnackbarHostState,
    isScrolledDown: Boolean,
    onCartConfirmClick: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var dialogState by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(true) }

    var customerName by remember { mutableStateOf("") }
    var tableNumber by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    var customerNameError by remember { mutableStateOf<String?>(null) }
    var tableNumberError by remember { mutableStateOf<String?>(null) }

    val productOut = dummyProductOutList

    Box(Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_SECTIONS.dp),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            AnimatedVisibility(
                visible = isScrolledDown
            ) {
                Card(
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        MaterialTheme.colorScheme.surfaceContainer
                    ),
                    modifier = Modifier.padding(horizontal = SizeChart.DEFAULT_SPACE.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = SizeChart.SMALL_SPACE.dp, horizontal = SizeChart.DEFAULT_SPACE.dp)
                    ) {
                        AnimatedVisibility(
                            visible = !isExpanded
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Card(
                                                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
                                            ) {
                                                Text(
                                                    stringResource(R.string.numberOrder, tableNumber).uppercase(),
                                                    style = MaterialTheme.typography.labelLarge.copy(
                                                        color = MaterialTheme.colorScheme.secondary
                                                    ),
                                                    modifier = Modifier.padding(horizontal = SizeChart.SIZE_SM.dp, vertical = SizeChart.SIZE_2XS.dp),
                                                )
                                            }
                                            Text(
                                                customerName,
                                                style = MaterialTheme.typography.titleLarge,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                        EditButton { isExpanded = !isExpanded }
                                    }
                                }
                                if (notes.isNotBlank()) {
                                    HorizontalDivider(
                                        thickness = 1.dp,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Text(notes)
                                }
                            }
                        }
                        AnimatedVisibility(
                            visible = isExpanded,
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
                                        onValueChange = { customerName = it },
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
                                        modifier = Modifier.weight(2f),
                                    )
                                    OutlinedTextField(
                                        isError = tableNumberError != null,
                                        value = tableNumber,
                                        onValueChange = { tableNumber = it },
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
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                OutlinedTextField(
                                    value = notes,
                                    onValueChange = { notes = it },
                                    label = { Text(stringResource(R.string.notes)) },
                                    singleLine = true,
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
                                    IconButton({isExpanded = !isExpanded}) {
                                        Icon(
                                            Icons.Default.ExpandLess,
                                            stringResource(R.string.collapse),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
                modifier = Modifier.padding(horizontal = SizeChart.DEFAULT_SPACE.dp)
            ) {
                Text(
                    stringResource(R.string.product),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = SizeChart.SMALL_SPACE.dp)
                )
                LazyColumn(
                    contentPadding = PaddingValues(bottom = SizeChart.DUAL_FAB_HEIGHT.dp),
                    verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .nestedScroll(nestedScrollConnection)
                ) {
                    item {
                        ProductOutList(
                            isEditable = true,
                            productOutItem = productOut
                        )
                    }
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.surface)
                .padding(SizeChart.DEFAULT_SPACE.dp)
        ) {
            AnimatedVisibility(
                visible = isScrolledDown
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(R.string.totalItem, 45)
                    )
                    Text(
                        Formatter.currency(10000.0),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            Button(
                onClick = {
                    customerNameError = Validator.isNotEmpty(context, customerName, context.getString(R.string.name))
                    tableNumberError = Validator.isNotEmpty(context, tableNumber, context.getString(R.string.tableNumber))

                    if (customerNameError == null && tableNumberError == null) {
                        // TODO Process Logic

                        dialogState = !dialogState
                    } else {
                        scope.launch { snackBarHostState.showSnackbar(context.getString(R.string.fillInAllFields)) }
                    }
                },
                Modifier.fillMaxWidth()
            ) { Text(stringResource(R.string.confirm)) }
        }
    }

    if (dialogState) {
        AlertDialog(
            icon = {
                Icon(
                    Icons.Default.ThumbUp,
                    stringResource(R.string.confirm)
                )
            },
            title = {
                Text(
                    stringResource(R.string.confirmSuccess),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    stringResource(R.string.confirmMessage),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            },
            onDismissRequest = { dialogState = !dialogState },
            confirmButton = {
                Button(
                    {
                        // TODO PRINT PDF
                        dialogState = !dialogState
                        scope.launch { snackBarHostState.showSnackbar(context.getString(R.string.printSuccess)) }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_TEXTS.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Print,
                            stringResource(R.string.print),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            stringResource(R.string.print),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            dismissButton = {
                TextButton(
                    { dialogState = !dialogState },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) { Text(stringResource(R.string.close)) }
            }
        )
    }
}