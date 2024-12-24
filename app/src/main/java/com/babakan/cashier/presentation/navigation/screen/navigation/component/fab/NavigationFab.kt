package com.babakan.cashier.presentation.navigation.screen.navigation.component.fab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.R
import com.babakan.cashier.data.sealed.AdminItem
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.cashier.viewmodel.CartViewModel
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.presentation.cashier.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductViewModel
import com.babakan.cashier.utils.animation.Duration
import com.babakan.cashier.utils.animation.slideInRightAnimation
import com.babakan.cashier.utils.animation.slideOutRightAnimation
import com.babakan.cashier.utils.constant.AuditState
import com.babakan.cashier.utils.constant.SizeChart
import com.google.firebase.Timestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NavigationFab(
    cartViewModel: CartViewModel = viewModel(),
    temporaryCartViewModel: TemporaryCartViewModel,
    temporaryItem: List<Map<String, Int>>,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    isHome: Boolean,
    isInvoice: Boolean,
    isAdminProduct: Boolean,
    isAdminCategory: Boolean,
    isAdminUser: Boolean,
    isFabShown: Boolean,
    isScrolledDown: Boolean,
    onSelectedAuditItemChange: (AdminItem) -> Unit,
    onAuditSheetStateChange: (AuditState) -> Unit,
    onAddNewItemChange: (Boolean) -> Unit,
    triggerEvent: (Boolean) -> Unit,
    shareBitmapFromComposable: () -> Unit
) {
    val addCartState by cartViewModel.addCartState.collectAsState()

    val context = LocalContext.current

    var temporaryItemCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(temporaryItem) {
        temporaryItemCount = temporaryItem.sumOf { it.values.sum() }
    }

    LaunchedEffect(addCartState) {
        when (addCartState) {
            is UiState.Success -> {
                triggerEvent(true)
                scope.launch(Dispatchers.Main) {
                    snackBarHostState.showSnackbar(
                        context.getString(R.string.addToCartSuccess)
                    )
                }
            }
            is UiState.Error -> {
                scope.launch(Dispatchers.Main) {
                    snackBarHostState.showSnackbar(
                        context.getString(R.string.addToCartFailed)
                    )
                }
            }
            else -> {}
        }
    }

    AnimatedVisibility(
        visible = isFabShown,
        enter = slideInRightAnimation(Duration.ANIMATION_SHORT),
        exit = slideOutRightAnimation(Duration.ANIMATION_SHORT)
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp)
        ) {
            AnimatedVisibility(
                visible = isHome
            ) {
                SmallFloatingActionButton(
                    { temporaryCartViewModel.clearTemporaryCart() },
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.tertiary
                ) {
                    Icon(
                        Icons.Default.Clear,
                        stringResource(R.string.clear)
                    )
                }
            }
            BadgedBox(
                {
                    this@Column.AnimatedVisibility(
                        isHome && temporaryItemCount > 0,
                        enter = slideInRightAnimation(Duration.ANIMATION_LONG),
                        exit = slideOutRightAnimation(Duration.ANIMATION_LONG)
                    ) {
                        Badge { Text(temporaryItemCount.toString()) }
                    }
                },
            ) {
                ExtendedFloatingActionButton(
                    expanded = !isScrolledDown,
                    onClick = {
                        when {
                            isHome -> {
                                val temporaryProductId = temporaryItem.map { it.keys.first() }
                                val temporaryProductQuantity = temporaryItem.map { it.values.first() }

                                scope.launch {
                                    cartViewModel.addCart(
                                        temporaryProductQuantity.mapIndexed { index, quantity ->
                                            ProductOutModel(
                                                createdAt = Timestamp.now(),
                                                updateAt = Timestamp.now(),
                                                productId = temporaryProductId[index],
                                                quantity = quantity
                                            )
                                        }
                                    )
                                    temporaryCartViewModel.clearTemporaryCart()
                                }
                            }
                            isAdminProduct -> {
                                onSelectedAuditItemChange(AdminItem.Product(ProductModel()))
                                onAuditSheetStateChange(AuditState.PRODUCT)
                                onAddNewItemChange(true)
                            }
                            isAdminCategory -> {
                                onSelectedAuditItemChange(AdminItem.Category(CategoryModel()))
                                onAuditSheetStateChange(AuditState.CATEGORY)
                                onAddNewItemChange(true)
                            }
                            isAdminUser -> {
                                onSelectedAuditItemChange(AdminItem.User(UserModel()))
                                onAuditSheetStateChange(AuditState.USER)
                                onAddNewItemChange(true)
                            }
                        }
                    },
                    icon = {
                        Icon(
                            Icons.Default.Add,
                            stringResource(R.string.add)
                        )
                    },
                    text = {
                        when {
                            isHome -> Text(stringResource(R.string.add))
                            isAdminProduct -> Text(stringResource(R.string.addMenu))
                            isAdminCategory -> Text(stringResource(R.string.addCategory))
                            isAdminUser -> Text(stringResource(R.string.addUser))
                        }
                    },
                )
            }
        }
    }
    AnimatedVisibility(isInvoice) {
        FloatingActionButton(
            onClick = { shareBitmapFromComposable() },
        ) {
            Icon(
                Icons.Default.Share,
                stringResource(R.string.share)
            )
        }
    }
}