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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.babakan.cashier.R
import com.babakan.cashier.common.list.ProductOutList
import com.babakan.cashier.common.ui.FullscreenLoading
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.cashier.screen.cart.component.CustomerData
import com.babakan.cashier.presentation.cashier.screen.cart.component.CustomerForm
import com.babakan.cashier.presentation.cashier.screen.cart.component.PrintInvoiceDialog
import com.babakan.cashier.presentation.cashier.viewmodel.CartViewModel
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.babakan.cashier.presentation.owner.model.TransactionModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductOutViewModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductViewModel
import com.babakan.cashier.presentation.owner.viewmodel.TransactionViewModel
import com.babakan.cashier.utils.animation.Duration
import com.babakan.cashier.utils.animation.fadeInAnimation
import com.babakan.cashier.utils.animation.fadeOutAnimation
import com.babakan.cashier.utils.animation.slideInBottomAnimation
import com.babakan.cashier.utils.animation.slideInTopAnimation
import com.babakan.cashier.utils.animation.slideOutBottomAnimation
import com.babakan.cashier.utils.animation.slideOutTopAnimation
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter
import com.babakan.cashier.utils.validator.Validator
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Cart(
    cartViewModel: CartViewModel,
    cart: List<ProductOutModel>,
    productViewModel: ProductViewModel = viewModel(),
    transactionViewModel: TransactionViewModel = viewModel(),
    productOutViewModel: ProductOutViewModel = viewModel(),
    currentUser: UserModel,
    nestedScrollConnection: NestedScrollConnection,
    snackBarHostState: SnackbarHostState,
    isScrolledDown: Boolean,
    cartTriggerEvent: (Boolean) -> Unit,
    onClickToPreview: () -> Unit
) {
    val productsState by productViewModel.fetchProductsState.collectAsState()
    val createTransactionState by transactionViewModel.createTransactionState.collectAsState()
    val clearCartState by cartViewModel.clearCartState.collectAsState()
    val addProductOutState by productOutViewModel.addProductOutState.collectAsState()

    var products by remember { mutableStateOf(emptyList<ProductModel>()) }
    var successTransactionId by remember { mutableStateOf("") }

    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(productsState, cart) {
        if (productsState is UiState.Loading) {
            showLoading = true
        } else if (productsState is UiState.Success) {
            showLoading = false
            products = (productsState as UiState.Success<List<ProductModel>>).data
        }
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var dialogState by remember { mutableStateOf(false) }

    var customerName by remember { mutableStateOf("") }
    var tableNumber by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    var customerNameError by remember { mutableStateOf<String?>(null) }
    var tableNumberError by remember { mutableStateOf<String?>(null) }

    val totalItem = cart.sumOf { it.quantity }
    val totalPrice = cart.sumOf { item ->
        val product = products.find { product -> product.id == item.productId }
        (product?.price ?: 0.0) * item.quantity
    }

    val required = customerName.isEmpty() || tableNumber.isEmpty() || customerNameError != null || tableNumberError != null

    LaunchedEffect(createTransactionState) {
        if (createTransactionState is UiState.Loading) {
            showLoading = true
        } else if (createTransactionState is UiState.Success) {
            cartViewModel.clearCart()
            cartTriggerEvent(true)

            showLoading = false
            dialogState = true
        } else if (createTransactionState is UiState.Error) {
            successTransactionId = ""
            scope.launch(Dispatchers.Main) {
                snackBarHostState.showSnackbar(
                    context.getString(R.string.transactionFailed)
                )
            }
            showLoading = false
        }
    }
    LaunchedEffect(clearCartState) {
        if (clearCartState is UiState.Success) {
            customerName = ""
            tableNumber = ""
            notes = ""
        }
    }
    LaunchedEffect(addProductOutState) {
        if (addProductOutState is UiState.Success) {
            showLoading = false
        }
    }

    val transactionIdBuilder = Timestamp.now().toDate().time.toString()
    val onCreateTransaction = {
        scope.launch {
            val prices = products.filter { it.id in cart.map { it.productId } }.map { it.price }
            val productOuts = cart.mapIndexed { index, product ->
                ProductOutModel(
                    productId = product.productId,
                    price = prices[index],
                    quantity = product.quantity
                )
            }
            successTransactionId = transactionIdBuilder
            transactionViewModel.createTransaction(
                TransactionModel(
                    transactionId = successTransactionId,
                    createdAt = Timestamp.now(),
                    updateAt = Timestamp.now(),
                    userId = currentUser.id,
                    tableNumber = tableNumber.toInt(),
                    customerName = customerName,
                    totalPrice = totalPrice,
                    notes = notes
                ),
                productOuts
            )
        }
    }

    AnimatedVisibility(
        cart.isEmpty(),
        enter = slideInBottomAnimation(Duration.ANIMATION_SHORT) + fadeInAnimation(Duration.ANIMATION_SHORT),
        exit = slideOutTopAnimation(Duration.ANIMATION_SHORT) + fadeOutAnimation(Duration.ANIMATION_SHORT)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(SizeChart.DEFAULT_SPACE.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_SECTIONS.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SizeChart.DEFAULT_SPACE.dp)
                ) {
                    AsyncImage(
                        model = R.drawable.anim_empty_cart,
                        contentDescription = stringResource(R.string.emptyCart),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .height(SizeChart.SIZE_MD.dp)
                            .width(SizeChart.SIZE_9XL.dp)
                            .background(MaterialTheme.colorScheme.surface)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(R.string.emptyCart),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        stringResource(R.string.emptyCartMessage),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
    AnimatedVisibility(
        cart.isNotEmpty(),
        enter = slideInTopAnimation(Duration.ANIMATION_SHORT) + fadeInAnimation(Duration.ANIMATION_SHORT),
        exit = slideOutBottomAnimation(Duration.ANIMATION_SHORT) + fadeOutAnimation(Duration.ANIMATION_SHORT)
    ) {
        Box(Modifier.fillMaxSize()) {
            if (showLoading) { FullscreenLoading() }
            Column(
                verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_SECTIONS.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
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
                            visible = if (required) false else !isScrolledDown,
                        ) {
                            CustomerData(
                                customerName = customerName,
                                tableNumber = tableNumber,
                                notes = notes,
                            )
                        }
                        AnimatedVisibility(
                            visible = if (required) true else isScrolledDown,
                        ) {
                            CustomerForm(
                                customerName = customerName,
                                customerNameError = customerNameError,
                                tableNumber = tableNumber,
                                tableNumberError = tableNumberError,
                                notes = notes,
                                onCustomerNameChange = { customerName = it },
                                onTableNumberChange = { tableNumber = it },
                                onCustomerNoteChange = { notes = it }
                            )
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
                                cartViewModel = cartViewModel,
                                productViewModel = productViewModel,
                                isOnCart = true,
                                productOutItem = cart,
                                triggerEvent = { cartTriggerEvent(it) }
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
                Button(
                    enabled = !required,
                    onClick = {
                        customerNameError = Validator.isNotEmpty(context, customerName, context.getString(R.string.name))
                        tableNumberError = Validator.isNotEmpty(context, tableNumber, context.getString(R.string.tableNumber))

                        if (customerNameError == null && tableNumberError == null) {
                            onCreateTransaction()
                        } else {
                            scope.launch { snackBarHostState.showSnackbar(context.getString(R.string.fillInAllFields)) }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text(stringResource(R.string.confirm)) }
            }
        }
    }
    if (dialogState) {
        PrintInvoiceDialog(
            onConfirm = {
                dialogState = false
                onClickToPreview()
            },
            onDismiss = { dialogState = false }
        )
    }
}