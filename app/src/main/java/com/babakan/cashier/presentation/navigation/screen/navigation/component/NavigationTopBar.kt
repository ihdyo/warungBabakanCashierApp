package com.babakan.cashier.presentation.navigation.screen.navigation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiFoodBeverage
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.babakan.cashier.R
import com.babakan.cashier.common.builder.IconLoader
import com.babakan.cashier.data.dummy.dummyCategoryList
import com.babakan.cashier.utils.constant.Constant
import com.babakan.cashier.utils.constant.SizeChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun NavigationTopBar(
    isHome: Boolean,
    isReport: Boolean,
    isAdmin: Boolean,
    isCart: Boolean,
    isAdminProduct: Boolean,
    isAdminCategory: Boolean,
    isAdminCashier: Boolean,
    isSearchActive: Boolean,
    snackBarHostState: SnackbarHostState,
    onSearchActiveChange: (Boolean) -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope,
    isScrolledDown: Boolean,
    pagerState: PagerState,
    tabs: List<Int>,
    navController : NavController
) {
    AnimatedVisibility(
        visible = isHome || isReport || isAdmin,
        enter = slideInVertically(initialOffsetY = { -it }, animationSpec = tween(Constant.ANIMATION_SHORT, easing = LinearOutSlowInEasing)),
        exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(Constant.ANIMATION_SHORT, easing = LinearOutSlowInEasing))
    ) {
        Column {
            MainSearchBar(
                scope = scope,
                drawerState = drawerState,
                isHome = isHome,
                isReport = isReport,
                isAdmin = isAdmin,
                isAdminProduct = isAdminProduct,
                isAdminCategory = isAdminCategory,
                isAdminCashier = isAdminCashier,
                isSearchActive = isSearchActive,
                snackBarHostState = snackBarHostState,
                onSearchActiveChange = onSearchActiveChange,
                navController = navController
            )
            AnimatedVisibility(
                visible = isScrolledDown && isHome,
            ) {
                val category = dummyCategoryList

                var selectedChipIndex by remember { mutableIntStateOf(0) }

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

                            FilterChip(
                                onClick = { selectedChipIndex = -1 },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.GridView,
                                        stringResource(R.string.all),
                                        tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.size(SizeChart.ICON_MEDIUM.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        stringResource(R.string.all),
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                selected = isSelected
                            )
                        }
                        itemsIndexed(category) { index, item ->
                            val isSelected = selectedChipIndex == index

                            FilterChip(
                                onClick = { selectedChipIndex = index },
                                leadingIcon = {
                                    IconLoader(
                                        imageUrl = item.iconUrl,
                                        size = SizeChart.ICON_MEDIUM.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                label = {
                                    Text(
                                        item.name,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                selected = isSelected
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
        enter = slideInVertically(initialOffsetY = { -it }, animationSpec = tween(Constant.ANIMATION_SHORT, easing = LinearOutSlowInEasing)),
        exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(Constant.ANIMATION_SHORT, easing = LinearOutSlowInEasing))
    ) {
        TopAppBar(
            { Text(stringResource(R.string.cart)) },
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
}