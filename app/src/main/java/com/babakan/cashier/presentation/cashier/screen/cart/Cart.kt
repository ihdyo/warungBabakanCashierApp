package com.babakan.cashier.presentation.cashier.screen.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.common.list.ProductOutList
import com.babakan.cashier.data.dummy.dummyProductOutList
import com.babakan.cashier.presentation.cashier.screen.cart.component.CustomerData
import com.babakan.cashier.presentation.cashier.screen.cart.component.CustomerForm
import com.babakan.cashier.presentation.cashier.screen.cart.component.PrintToPDFDialog
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
    val totalItem = productOut.sumOf { it.quantity }
    val totalPrice = productOut.sumOf { it.price * it.quantity }

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
                            CustomerData(
                                customerName = customerName,
                                tableNumber = tableNumber,
                                notes = notes,
                            ) { isExpanded = !isExpanded }
                        }
                        AnimatedVisibility(
                            visible = isExpanded,
                        ) {
                            CustomerForm(
                                customerName = customerName,
                                customerNameError = customerNameError,
                                tableNumber = tableNumber,
                                tableNumberError = tableNumberError,
                                notes = notes,
                                onCustomerNameChange = { customerName = it },
                                onTableNumberChange = { tableNumber = it },
                                onCustomerNoteChange = { notes = it },
                                onExpandClick = { isExpanded = !isExpanded }
                            )
                        }
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
                modifier = Modifier.padding(horizontal = SizeChart.DEFAULT_SPACE.dp)
            ) {
                Text(
                    stringResource(R.string.menu),
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
            AnimatedVisibility(isScrolledDown) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(R.string.totalItem, totalItem)
                    )
                    Text(
                        Formatter.currency(totalPrice),
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
                        onCartConfirmClick()
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
        PrintToPDFDialog(
            context = context,
            scope = scope,
            snackBarHostState = snackBarHostState
        ) { dialogState = !dialogState }
    }
}