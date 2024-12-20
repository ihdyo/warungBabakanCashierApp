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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.babakan.cashier.R
import com.babakan.cashier.common.component.SearchChipComponent
import com.babakan.cashier.data.dummy.dummyCategoryList
import com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.MainSearchBar
import com.babakan.cashier.presentation.owner.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.utils.animation.Duration
import com.babakan.cashier.utils.animation.slideInBottomAnimation
import com.babakan.cashier.utils.animation.slideOutTopAnimation
import com.babakan.cashier.utils.constant.SizeChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun NavigationTopBar(
    temporaryCartViewModel: TemporaryCartViewModel,
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
    navController : NavController
) {

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
                isScrolledDown = isScrolledDown
            )
            AnimatedVisibility(
                visible = isScrolledDown && isHome,
            ) {
                val category = dummyCategoryList

                var selectedChipIndex by remember { mutableIntStateOf(-1) }

                Column {
                    Text(
                        stringResource(R.string.product),
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
                                onClick = { selectedChipIndex = -1 },
                                icon = Icons.Filled.GridView,
                                isSelected = isSelected,
                                label = stringResource(R.string.all)
                            )
                        }
                        itemsIndexed(category) { index, item ->
                            val isSelected = selectedChipIndex == index

                            SearchChipComponent(
                                onClick = { selectedChipIndex = index },
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
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.cart))
                    IconButton({ dialogState = !dialogState }) {
                        Icon(
                            Icons.Default.Close,
                            stringResource(R.string.cart)
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
                        // TODO Clear cart
                        dialogState = !dialogState
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