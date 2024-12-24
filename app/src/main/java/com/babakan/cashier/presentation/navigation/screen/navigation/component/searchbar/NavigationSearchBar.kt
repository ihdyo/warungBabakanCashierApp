package com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar

import android.graphics.Picture
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.babakan.cashier.common.component.DateRangePickerComponent
import com.babakan.cashier.common.component.SearchChipComponent
import com.babakan.cashier.data.sealed.AdminItem
import com.babakan.cashier.data.ui.listOfReportSearch
import com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component.SearchAdminProduct
import com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component.SearchAdminUser
import com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component.SearchAdminCategory
import com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component.SearchProduct
import com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar.component.SearchTransaction
import com.babakan.cashier.presentation.cashier.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.utils.animation.Duration
import com.babakan.cashier.utils.animation.fadeInAnimation
import com.babakan.cashier.utils.animation.fadeOutAnimation
import com.babakan.cashier.utils.animation.slideInLeftAnimation
import com.babakan.cashier.utils.animation.slideInRightAnimation
import com.babakan.cashier.utils.animation.slideOutLeftAnimation
import com.babakan.cashier.utils.animation.slideOutRightAnimation
import com.babakan.cashier.utils.constant.AuditState
import com.babakan.cashier.utils.constant.MainScreenState
import com.babakan.cashier.utils.constant.SizeChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun MainSearchBar(
    temporaryCartViewModel: TemporaryCartViewModel,
    scope: CoroutineScope,
    drawerState: DrawerState,
    isHome: Boolean,
    isTransaction: Boolean,
    isAdmin: Boolean,
    isAdminProduct: Boolean,
    isAdminCategory: Boolean,
    isAdminUser: Boolean,
    isSearchActive: Boolean,
    snackBarHostState: SnackbarHostState,
    onSearchActiveChange: (Boolean) -> Unit,
    navController: NavController,
    nestedScrollConnection: NestedScrollConnection,
    isScrolledDown: Boolean,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (AdminItem) -> Unit,
    picture: Picture
) {
    val context = LocalContext.current

    var selectedChipIndex by remember { mutableIntStateOf(0) }
    var query by remember { mutableStateOf("") }
    var querySearch by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDateRange by remember { mutableStateOf<Pair<Long?, Long?>?>(null) }

    var resultCount by remember { mutableIntStateOf(0) }
    val isResultNotNull = resultCount > 0

    val isReportByTransactionNumber = selectedChipIndex == 0
    val isReportByCashier = selectedChipIndex == 1
    val isReportByDate = selectedChipIndex == 2

    val onSearch = {
        querySearch = query
    }

    val onBack = {
        onSearchActiveChange(false)
        selectedChipIndex = 0
        query = ""
        querySearch = ""
        selectedDateRange = null
    }

    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = { onSearch() },
        active = isSearchActive,
        onActiveChange = { onSearchActiveChange(it) },
        enabled = !isReportByDate,
        placeholder = {
            Text(
                when {
                    isHome -> stringResource(R.string.menuSearch)
                    isTransaction -> when {
                        isReportByTransactionNumber -> stringResource(R.string.transactionSearch)
                        isReportByCashier -> stringResource(R.string.cashierSearch)
                        isReportByDate -> stringResource(R.string.dateSearch)
                        else -> stringResource(R.string.search)
                    }
                    isAdmin -> when {
                        isAdminProduct -> stringResource(R.string.menuSearch)
                        isAdminCategory -> stringResource(R.string.categorySearch)
                        isAdminUser -> stringResource(R.string.cashierSearch)
                        else -> stringResource(R.string.search)
                    }
                    else -> stringResource(R.string.search)
                },
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if (isReportByDate) MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.5f
                    ) else MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        },
        leadingIcon = {
            AnimatedVisibility(
                isSearchActive,
                enter = slideInRightAnimation(Duration.ANIMATION_SHORT) + fadeInAnimation(Duration.ANIMATION_SHORT),
                exit = slideOutLeftAnimation(Duration.ANIMATION_SHORT) + fadeOutAnimation(Duration.ANIMATION_SHORT)
            ) {
                IconButton({ onBack() }) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        stringResource(R.string.back),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            AnimatedVisibility(
                !isSearchActive,
                enter = slideInRightAnimation(Duration.ANIMATION_SHORT) + fadeInAnimation(Duration.ANIMATION_SHORT),
                exit = slideOutLeftAnimation(Duration.ANIMATION_SHORT) + fadeOutAnimation(Duration.ANIMATION_SHORT)
            ) {
                IconButton({ scope.launch { drawerState.open() } }) {
                    Icon(
                        Icons.Default.Menu,
                        stringResource(R.string.info)
                    )
                }
            }
        },
        trailingIcon = {
            AnimatedVisibility(
                isSearchActive,
                enter = slideInLeftAnimation(Duration.ANIMATION_SHORT) + fadeInAnimation(Duration.ANIMATION_SHORT),
                exit = slideOutRightAnimation(Duration.ANIMATION_SHORT) + fadeOutAnimation(Duration.ANIMATION_SHORT)
            ) {
                AnimatedVisibility(
                    isReportByDate,
                    enter = slideInLeftAnimation(Duration.ANIMATION_SHORT) + fadeInAnimation(Duration.ANIMATION_SHORT),
                    exit = slideOutRightAnimation(Duration.ANIMATION_SHORT) + fadeOutAnimation(Duration.ANIMATION_SHORT)
                ) {
                    IconButton({ showDatePicker = true }) {
                        Icon(
                            Icons.Default.CalendarMonth,
                            stringResource(R.string.date),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                AnimatedVisibility(
                    !isReportByDate,
                    enter = slideInRightAnimation(Duration.ANIMATION_SHORT) + fadeInAnimation(Duration.ANIMATION_SHORT),
                    exit = slideOutLeftAnimation(Duration.ANIMATION_SHORT) + fadeOutAnimation(Duration.ANIMATION_SHORT)
                ) {
                    IconButton({ onSearch() }) {
                        Icon(
                            Icons.Default.Search,
                            stringResource(R.string.search)
                        )
                    }
                }
            }
            AnimatedVisibility(
                !isSearchActive,
                enter = slideInLeftAnimation(Duration.ANIMATION_SHORT) + fadeInAnimation(Duration.ANIMATION_SHORT),
                exit = slideOutRightAnimation(Duration.ANIMATION_SHORT) + fadeOutAnimation(Duration.ANIMATION_SHORT)
            ) {
                AnimatedVisibility(
                    visible = isHome,
                    enter = slideInLeftAnimation(Duration.ANIMATION_SHORT) + fadeInAnimation(Duration.ANIMATION_SHORT),
                    exit = slideOutRightAnimation(Duration.ANIMATION_SHORT) + fadeOutAnimation(Duration.ANIMATION_SHORT)
                ) {
                    IconButton({
                        scope.launch {
                            navController.navigate(MainScreenState.CART.name)
                        }
                    }) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            stringResource(R.string.cart)
                        )
                    }
                }
                AnimatedVisibility(
                    visible = isAdmin,
                    enter = slideInLeftAnimation(Duration.ANIMATION_SHORT) + fadeInAnimation(Duration.ANIMATION_SHORT),
                    exit = slideOutRightAnimation(Duration.ANIMATION_SHORT) + fadeOutAnimation(Duration.ANIMATION_SHORT)
                ) {
                    IconButton(
                        {
                            scope.launch {
                                snackBarHostState.showSnackbar(context.getString(R.string.loginAsOwner))
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.VerifiedUser,
                            stringResource(R.string.admin),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = animateDpAsState(
                    label = stringResource(R.string.search),
                    targetValue = if (isSearchActive) SizeChart.SIZE_0.dp else SizeChart.DEFAULT_SPACE.dp
                ).value
            )
            .padding(
                bottom = animateDpAsState(
                    label = stringResource(R.string.search),
                    targetValue = if (isSearchActive) SizeChart.SIZE_0.dp else SizeChart.SMALL_SPACE.dp
                ).value
            ),
    ) {
        BackHandler(isSearchActive) { onBack() }
        AnimatedVisibility(isTransaction && isScrolledDown) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
                contentPadding = PaddingValues(
                    vertical = SizeChart.SMALL_SPACE.dp,
                    horizontal = SizeChart.DEFAULT_SPACE.dp
                )
            ) {
                itemsIndexed(listOfReportSearch(context)) { index, item ->
                    SearchChipComponent(
                        onClick = {
                            selectedChipIndex = index
                            query = ""
                            querySearch = ""
                            selectedDateRange = null
                        },
                        isSelected = selectedChipIndex == index,
                        label = item
                    )
                }
            }
        }
        AnimatedVisibility(querySearch.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SizeChart.DEFAULT_SPACE.dp, vertical = SizeChart.BETWEEN_ITEMS.dp)
            ) {
                Text(
                    if (isResultNotNull) stringResource(R.string.searchQuery, querySearch) else stringResource(R.string.noResult, querySearch)
                )
                AnimatedVisibility(isResultNotNull) {
                    Text(
                        stringResource(R.string.showingResult, resultCount),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        AnimatedVisibility(isHome) {
            SearchProduct(
                temporaryCartViewModel = temporaryCartViewModel,
                nestedScrollConnection = nestedScrollConnection,
                query = querySearch,
                onResultCountChange = { newCount ->
                    resultCount = newCount
                },
                isSearchActive = isSearchActive,
            )
        }
        AnimatedVisibility(isTransaction) {
            SearchTransaction(
                nestedScrollConnection = nestedScrollConnection,
                query = querySearch,
                dateRange = selectedDateRange,
                onResultCountChange = { newCount ->
                    resultCount = newCount
                },
                isReportByTransactionNumber = isReportByTransactionNumber,
                isReportByCashier = isReportByCashier,
                isReportByDate = isReportByDate,
                isSearchActive = isSearchActive,
                navController = navController
            )
        }
        AnimatedVisibility(isAdmin && isAdminProduct) {
            SearchAdminProduct(
                temporaryCartViewModel = temporaryCartViewModel,
                nestedScrollConnection = nestedScrollConnection,
                query = querySearch,
                onResultCountChange = { newCount ->
                    resultCount = newCount
                },
                onAuditStateChange = onAuditStateChange,
                onItemSelected = { item ->
                    onItemSelected(AdminItem.Product(item))
                },
                isSearchActive = isSearchActive,
            )
        }
        AnimatedVisibility(isAdmin && isAdminCategory) {
            SearchAdminCategory(
                nestedScrollConnection = nestedScrollConnection,
                query = querySearch,
                onResultCountChange = { newCount ->
                    resultCount = newCount
                },
                onAuditStateChange = onAuditStateChange,
                onItemSelected = { item ->
                    onItemSelected(AdminItem.Category(item))
                },
                isSearchActive = isSearchActive,
            )
        }
        AnimatedVisibility(isAdmin && isAdminUser) {
            SearchAdminUser(
                nestedScrollConnection = nestedScrollConnection,
                query = querySearch,
                onResultCountChange = { newCount ->
                    resultCount = newCount
                },
                onAuditStateChange = onAuditStateChange,
                onItemSelected = { item ->
                    onItemSelected(AdminItem.User(item))
                },
                isSearchActive = isSearchActive,
            )
        }
        if (showDatePicker) {
            DateRangePickerComponent(
                onDateRangeSelected = { range ->
                    selectedDateRange = range
                },
                onDismiss = { showDatePicker = false }
            )
        }
    }
}