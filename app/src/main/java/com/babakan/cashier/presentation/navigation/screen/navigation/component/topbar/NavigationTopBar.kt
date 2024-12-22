package com.babakan.cashier.presentation.navigation.screen.navigation.component.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.babakan.cashier.R
import com.babakan.cashier.common.component.SearchChipComponent
import com.babakan.cashier.common.ui.CommonDialog
import com.babakan.cashier.data.sealed.AdminItem
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.cashier.viewmodel.CartViewModel
import com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.MainSearchBar
import com.babakan.cashier.presentation.cashier.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.babakan.cashier.utils.animation.Duration
import com.babakan.cashier.utils.animation.slideInBottomAnimation
import com.babakan.cashier.utils.animation.slideInLeftAnimation
import com.babakan.cashier.utils.animation.slideOutRightAnimation
import com.babakan.cashier.utils.animation.slideOutTopAnimation
import com.babakan.cashier.utils.constant.AuditState
import com.babakan.cashier.utils.constant.SizeChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun NavigationTopBar(
    cartViewModel: CartViewModel,
    temporaryCartViewModel: TemporaryCartViewModel,
    cart: List<ProductOutModel>,
    categories: List<CategoryModel>,
    onCategorySelected: (CategoryModel) -> Unit,
    onAllCategorySelected: () -> Unit,
    isHome: Boolean,
    isReport: Boolean,
    isAdmin: Boolean,
    isCart: Boolean,
    isAdminProduct: Boolean,
    isAdminCategory: Boolean,
    isAdminUser: Boolean,
    isSearchActive: Boolean,
    snackBarHostState: SnackbarHostState,
    onSearchActiveChange: (Boolean) -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope,
    nestedScrollConnection: NestedScrollConnection,
    isScrolledDown: Boolean,
    pagerState: PagerState,
    tabs: List<Int>,
    navController : NavController,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (AdminItem) -> Unit,
    triggerEvent: (Boolean) -> Unit
) {
    val clearCartState by cartViewModel.clearCartState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(clearCartState) {
        when (clearCartState) {
            is UiState.Success -> {
                triggerEvent(true)
                scope.launch(Dispatchers.Main) {
                    snackBarHostState.showSnackbar(
                        context.getString(R.string.clearCartSuccess)
                    )
                }
            }
            is UiState.Error -> {
                scope.launch(Dispatchers.Main) {
                    snackBarHostState.showSnackbar(
                        context.getString(R.string.clearCartFailed)
                    )
                }
            }
            else -> {}
        }
    }

    var dialogState by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = isHome || isReport || isAdmin,
        enter = slideInBottomAnimation(Duration.ANIMATION_SHORT),
        exit = slideOutTopAnimation(Duration.ANIMATION_SHORT)
    ) {
        Column {
            MainSearchBar(
                temporaryCartViewModel = temporaryCartViewModel,
                scope = scope,
                drawerState = drawerState,
                isHome = isHome,
                isTransaction = isReport,
                isAdmin = isAdmin,
                isAdminProduct = isAdminProduct,
                isAdminCategory = isAdminCategory,
                isAdminUser = isAdminUser,
                isSearchActive = isSearchActive,
                snackBarHostState = snackBarHostState,
                onSearchActiveChange = onSearchActiveChange,
                navController = navController,
                nestedScrollConnection = nestedScrollConnection,
                isScrolledDown = isScrolledDown,
                onAuditStateChange = onAuditStateChange,
                onItemSelected = onItemSelected
            )
            AnimatedVisibility(
                visible = isScrolledDown && isHome,
            ) {
                var selectedChipIndex by remember { mutableIntStateOf(-1) }

                Column {
                    Text(
                        stringResource(R.string.menu),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(vertical = SizeChart.SMALL_SPACE.dp)
                            .padding(start = SizeChart.DEFAULT_SPACE.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
                        contentPadding = PaddingValues(horizontal = SizeChart.DEFAULT_SPACE.dp)
                    ) {
                        item {
                            val isSelected = selectedChipIndex == -1

                            SearchChipComponent(
                                onClick = {
                                    selectedChipIndex = -1
                                    onAllCategorySelected()
                                },
                                icon = Icons.Filled.GridView,
                                isSelected = isSelected,
                                label = stringResource(R.string.all)
                            )
                        }
                        itemsIndexed(categories) { index, item ->
                            val isSelected = selectedChipIndex == index

                            SearchChipComponent(
                                onClick = {
                                    selectedChipIndex = index
                                    onCategorySelected(item)
                                },
                                iconUrl = item.iconUrl,
                                isSelected = isSelected,
                                label = item.name
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = isScrolledDown && isReport,
            ) {
                Text(
                    stringResource(R.string.report),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(vertical = SizeChart.SMALL_SPACE.dp)
                        .padding(start = SizeChart.DEFAULT_SPACE.dp)
                )
            }
            AnimatedVisibility(
                visible = isScrolledDown && isAdmin,
            ) {
                TabRow(pagerState.currentPage) {
                    tabs.forEachIndexed { index, name ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = { Text(stringResource(name)) }
                        )
                    }
                }
            }
        }
    }
    AnimatedVisibility(
        visible = isCart,
        enter = slideInBottomAnimation(Duration.ANIMATION_SHORT),
        exit = slideOutTopAnimation(Duration.ANIMATION_SHORT)
    ) {
        TopAppBar(
            { Text(stringResource(R.string.cart)) },
            actions = {
                AnimatedVisibility(
                    cart.isNotEmpty(),
                    enter = slideInLeftAnimation(Duration.ANIMATION_SHORT),
                    exit = slideOutRightAnimation(Duration.ANIMATION_SHORT)
                ) {
                    IconButton({ dialogState = !dialogState }) {
                        Icon(
                            Icons.Default.DeleteOutline,
                            stringResource(R.string.delete)
                        )
                    }
                }
            },
            navigationIcon = {
                IconButton({ navController.navigateUp() }) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        stringResource(R.string.back)
                    )
                }
            }
        )
    }
    if (dialogState) {
        CommonDialog(
            icon = Icons.Default.DeleteOutline,
            title = stringResource(R.string.clearCart),
            body = stringResource(R.string.clearCartConfirmation),
            onConfirm = {
                cartViewModel.clearCart()
                dialogState = !dialogState
            },
            confirmText = stringResource(R.string.clear),
            onDismiss = { dialogState = !dialogState },
            dismissText = stringResource(R.string.cancel)
        )
    }
}