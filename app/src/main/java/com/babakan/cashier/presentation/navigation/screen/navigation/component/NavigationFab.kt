package com.babakan.cashier.presentation.navigation.screen.navigation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.babakan.cashier.R
import com.babakan.cashier.presentation.owner.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.utils.constant.Constant
import com.babakan.cashier.utils.constant.SizeChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationFab(
    temporaryCartViewModel: TemporaryCartViewModel,
    isHome: Boolean,
    isAdminProduct: Boolean,
    isAdminCategory: Boolean,
    isAdminCashier: Boolean,
    isFabShown: Boolean,
    temporaryTotalQuantity: Int,
    mainScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    onDrawerStateChange: (DrawerValue) -> Unit,
) {
    val context = LocalContext.current
    var dialogState by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = isFabShown,
        enter = slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(Constant.ANIMATION_SHORT)),
        exit = slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(Constant.ANIMATION_SHORT))
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
                        visible = isHome
                    ) {
                        Badge { Text(temporaryTotalQuantity.toString()) }
                    }
                },
            ) {
                FloatingActionButton(
                    {
                        when {
                            isHome -> {
                                // TODO Add To Cart
                            }
                            isAdminCashier -> {
                                // TODO Add Cashier
                            }
                            isAdminCategory -> {
                                // TODO Add Category
                            }
                            isAdminProduct -> {
                                // TODO Add Product
                            }
                        }
                    },
                ) {
                    Icon(
                        Icons.Default.Add,
                        stringResource(R.string.add)
                    )
                }
            }
        }
    }

    if (dialogState) {
        AlertDialog(
            icon = {
                Icon(
                    Icons.Default.Clear,
                    stringResource(R.string.clearCart)
                )
            },
            title = {
                Text(
                    stringResource(R.string.clearCart),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    stringResource(R.string.clearCartConfirmation),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            },
            onDismissRequest = { dialogState = !dialogState },
            confirmButton = {
                Button(
                    {
                        dialogState = !dialogState
                        onDrawerStateChange(DrawerValue.Closed)

                        mainScope.launch {
                            temporaryCartViewModel.clearTemporaryCart()
                            snackBarHostState.showSnackbar(getString(context, R.string.clearCartSuccess))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(stringResource(R.string.clear))
                }
            },
            dismissButton = {
                TextButton(
                    { dialogState = !dialogState },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) { Text(stringResource(R.string.cancel),) }
            }
        )
    }
}