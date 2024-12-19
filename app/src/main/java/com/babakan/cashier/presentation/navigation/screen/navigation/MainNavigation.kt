package com.babakan.cashier.presentation.navigation.screen.navigation

import Admin
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.babakan.cashier.R
import com.babakan.cashier.presentation.cashier.screen.home.Home
import com.babakan.cashier.presentation.navigation.screen.navigation.component.NavigationBottomBar
import com.babakan.cashier.presentation.navigation.screen.navigation.component.NavigationDrawer
import com.babakan.cashier.presentation.navigation.screen.navigation.component.NavigationFab
import com.babakan.cashier.presentation.navigation.screen.navigation.component.NavigationTopBar
import com.babakan.cashier.presentation.owner.screen.report.Report
import com.babakan.cashier.presentation.owner.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.utils.constant.Constant
import com.babakan.cashier.utils.constant.MainScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

@ExperimentalMaterial3Api
@Composable
fun MainNavigation(
    temporaryCartViewModel: TemporaryCartViewModel = viewModel(),
    authScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    onNavigateToLogin: () -> Unit
) {
    val mainScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    var isSearchActive by remember { mutableStateOf(false) }

    val tabs = listOf(R.string.product, R.string.category, R.string.cashier)
    var selectedAdminTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(selectedAdminTabIndex, pageCount = { tabs.size })
    val onSelectedAdminTabIndex: (Int) -> Unit = { index ->
        mainScope.launch {
            pagerState.scrollToPage(index)
        }
        selectedAdminTabIndex = index
    }

    val isHome = currentDestination == MainScreenState.HOME.name
    val isReport = currentDestination == MainScreenState.REPORT.name
    val isAdmin = currentDestination == MainScreenState.ADMIN.name

    val isAdminProduct = pagerState.currentPage == 0
    val isAdminCategory = pagerState.currentPage == 1
    val isAdminCashier = pagerState.currentPage == 2

    var isScrolledDown by remember { mutableStateOf(false) }
    if (isHome || isReport || isAdmin) {
        isScrolledDown = true
    }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (available.y < -1) {
                    isScrolledDown = false
                }
                if (available.y > 1) {
                    isScrolledDown = true
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                return Velocity.Zero
            }
        }
    }

    val temporaryTotalQuantity by temporaryCartViewModel.temporaryTotalQuantity.collectAsState()
    val isTemporaryProductEmpty = temporaryTotalQuantity == 0
    val isFabShown = !isSearchActive && ((isHome && !isTemporaryProductEmpty) || isAdmin)
    if (isReport || isAdmin) {
        temporaryCartViewModel.clearTemporaryCart()
    }

    val isOwner = true // TODO Change this
    val testNumber = Random.nextInt(0, 10) // TODO Change this
    val cartItemCount = testNumber
    val isCartEmpty = cartItemCount == 0

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawer(
                authScope = authScope,
                mainScope = mainScope,
                snackBarHostState = snackBarHostState,
                onDrawerStateChange = { mainScope.launch { drawerState.close() } },
                onNavigateToLogin = onNavigateToLogin
            )
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            topBar = {
                NavigationTopBar(
                    isHome = isHome,
                    isReport = isReport,
                    isAdmin = isAdmin,
                    isAdminProduct = isAdminProduct,
                    isAdminCategory = isAdminCategory,
                    isAdminCashier = isAdminCashier,
                    isSearchActive = isSearchActive,
                    snackBarHostState = snackBarHostState,
                    onSearchActiveChange = { isSearchActive = it },
                    drawerState = drawerState,
                    scope = mainScope,
                    isCartEmpty = isCartEmpty,
                    cartItemCount = cartItemCount,
                    isScrolledDown = isScrolledDown,
                    pagerState = pagerState,
                    tabs = tabs
                )
            },
            floatingActionButton = {
                NavigationFab(
                    temporaryCartViewModel = temporaryCartViewModel,
                    isHome = isHome,
                    isAdminProduct = isAdminProduct,
                    isAdminCategory = isAdminCategory,
                    isAdminCashier = isAdminCashier,
                    isFabShown = isFabShown,
                    temporaryTotalQuantity = temporaryTotalQuantity,
                    mainScope = mainScope,
                    snackBarHostState = snackBarHostState,
                    onDrawerStateChange = { DrawerValue.Open },
                )
            },
            bottomBar = {
                if (isOwner) {
                    NavigationBottomBar(
                        navController = navController,
                        currentDestination = currentDestination,
                        isSearchActive = isSearchActive
                    )
                }
            },
        ) { paddingValues ->
            NavHost(
                navController,
                MainScreenState.HOME.name,
                enterTransition = { scaleIn(tween(Constant.ANIMATION_SHORT), 0.96f) + fadeIn(tween(Constant.ANIMATION_SHORT)) },
                exitTransition = { scaleOut(tween(Constant.ANIMATION_SHORT), 0.96f) + fadeOut(tween(Constant.ANIMATION_SHORT)) },
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(MainScreenState.HOME.name) {
                    Home(
                        temporaryCartViewModel,
                        nestedScrollConnection,
                        isFabShown
                    )
                }
                composable(MainScreenState.REPORT.name) {
                    Report(
                        nestedScrollConnection
                    )
                }
                composable(MainScreenState.ADMIN.name) {
                    Admin(
                        nestedScrollConnection,
                        pagerState,
                        onSelectedAdminTabIndex
                    )
                }
            }
        }
    }
}