package com.babakan.cashier.presentation.navigation.screen.main

import Admin
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.babakan.cashier.R
import com.babakan.cashier.data.ui.listOfNavigationItems
import com.babakan.cashier.presentation.cashier.screen.home.Home
import com.babakan.cashier.presentation.navigation.screen.bottombar.MainBottomBar
import com.babakan.cashier.presentation.navigation.screen.drawer.MainDrawer
import com.babakan.cashier.presentation.navigation.screen.fab.MainFab
import com.babakan.cashier.presentation.navigation.screen.topbar.MainTopBar
import com.babakan.cashier.presentation.owner.screen.report.Report
import com.babakan.cashier.utils.constant.Constant
import com.babakan.cashier.utils.constant.MainScreenState
import com.babakan.cashier.utils.constant.SizeChart
import kotlinx.coroutines.launch
import kotlin.random.Random

@ExperimentalMaterial3Api
@Composable
fun MainNavigation() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    var isSearchActive by remember { mutableStateOf(false) }
    var isScrolledDown by remember { mutableStateOf(true) }
    var selectedAdminTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(R.string.product, R.string.category, R.string.cashier)
    val pagerState = rememberPagerState(selectedAdminTabIndex, pageCount = { tabs.size })
    val onSelectedAdminTabIndex: (Int) -> Unit = { index ->
        scope.launch {
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

    val isOwner = true // TODO Change this
    val testNumber = Random.nextInt(0, 10) // TODO Change this
    val cartItemCount = testNumber
    val isCartEmpty = cartItemCount == 0

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { MainDrawer() }
    ) {
        Scaffold(
            topBar = {
                MainTopBar(
                    isHome = isHome,
                    isReport = isReport,
                    isAdmin = isAdmin,
                    isAdminProduct = isAdminProduct,
                    isAdminCategory = isAdminCategory,
                    isAdminCashier = isAdminCashier,
                    isSearchActive = isSearchActive,
                    onSearchActiveChange = { isSearchActive = it },
                    drawerState = drawerState,
                    scope = scope,
                    isCartEmpty = isCartEmpty,
                    cartItemCount = cartItemCount,
                    isScrolledDown = isScrolledDown,
                    pagerState = pagerState,
                    tabs = tabs
                )
            },
            floatingActionButton = {
                MainFab(
                    isHome = isHome,
                    isAdmin = isAdmin,
                    isAdminProduct = isAdminProduct,
                    isAdminCategory = isAdminCategory,
                    isAdminCashier = isAdminCashier,
                    isCartEmpty = isCartEmpty,
                    isSearchActive = isSearchActive
                )
            },
            bottomBar = {
                if (isOwner) {
                    MainBottomBar(
                        navController = navController,
                        currentDestination = currentDestination,
                        isSearchActive = isSearchActive
                    )
                }
            },
        ) { innerPadding ->
            NavHost(
                navController,
                MainScreenState.HOME.name,
                enterTransition = { scaleIn(tween(Constant.ANIMATION_SHORT), 0.96f) + fadeIn(tween(Constant.ANIMATION_SHORT)) },
                exitTransition = { scaleOut(tween(Constant.ANIMATION_SHORT), 0.96f) + fadeOut(tween(Constant.ANIMATION_SHORT)) },
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(MainScreenState.HOME.name) { Home(nestedScrollConnection) }
                composable(MainScreenState.REPORT.name) { Report(nestedScrollConnection) }
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