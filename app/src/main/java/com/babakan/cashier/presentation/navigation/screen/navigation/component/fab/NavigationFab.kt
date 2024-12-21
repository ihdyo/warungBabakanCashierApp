package com.babakan.cashier.presentation.navigation.screen.navigation.component.fab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.babakan.cashier.R
import com.babakan.cashier.common.ui.CommonDialog
import com.babakan.cashier.data.sealed.AdminItem
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.presentation.cashier.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.utils.animation.Duration
import com.babakan.cashier.utils.animation.slideInRightAnimation
import com.babakan.cashier.utils.animation.slideOutRightAnimation
import com.babakan.cashier.utils.constant.AuditState
import com.babakan.cashier.utils.constant.SizeChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationFab(
    temporaryCartViewModel: TemporaryCartViewModel,
    temporaryTotalQuantity: Int,
    isHome: Boolean,
    isAdminProduct: Boolean,
    isAdminCategory: Boolean,
    isAdminUser: Boolean,
    isFabShown: Boolean,
    isScrolledDown: Boolean,
    mainScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    onDrawerStateChange: (DrawerValue) -> Unit,
    onSelectedAuditItemChange: (AdminItem) -> Unit,
    onAuditSheetStateChange: (AuditState) -> Unit,
    onAddNewItemChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    var dialogState by remember { mutableStateOf(false) }

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
                    {
                        dialogState = !dialogState
                    },
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
                        isHome && temporaryTotalQuantity > 0,
                        enter = slideInRightAnimation(Duration.ANIMATION_LONG),
                        exit = slideOutRightAnimation(Duration.ANIMATION_LONG)
                    ) {
                        Badge { Text(temporaryTotalQuantity.toString()) }
                    }
                },
            ) {
                ExtendedFloatingActionButton(
                    expanded = !isScrolledDown,
                    onClick = {
                        when {
                            isHome -> {
                                // TODO: Add To Cart
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

    if (dialogState) {
        CommonDialog(
            icon = Icons.Default.Clear,
            title = stringResource(R.string.clearCart),
            body = stringResource(R.string.clearCartConfirmation),
            onConfirm = {
                dialogState = !dialogState
                onDrawerStateChange(DrawerValue.Closed)

                mainScope.launch {
                    temporaryCartViewModel.clearTemporaryCart()
                    snackBarHostState.showSnackbar(getString(context, R.string.clearCartSuccess))
                }
            },
            confirmText = stringResource(R.string.clear),
            onDismiss = { dialogState = !dialogState },
            dismissText = stringResource(R.string.cancel)
        )
    }
}