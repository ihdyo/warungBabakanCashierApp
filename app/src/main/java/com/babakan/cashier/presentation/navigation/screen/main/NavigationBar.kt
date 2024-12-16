package com.babakan.cashier.presentation.navigation.screen.main

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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
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
import com.babakan.cashier.presentation.owner.screen.admin.Admin
import com.babakan.cashier.presentation.owner.screen.report.Report
import com.babakan.cashier.utils.constant.MainScreenState
import com.babakan.cashier.utils.constant.Size
import kotlinx.coroutines.launch
import kotlin.random.Random

@ExperimentalMaterial3Api
@Composable
fun MainNavigation() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scrollState = rememberScrollState()

    val navController = rememberNavController()
    var isSearchActive by remember { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val isHome = currentDestination == MainScreenState.HOME.name
    val isReport = currentDestination == MainScreenState.REPORT.name
    val isAdmin = currentDestination == MainScreenState.ADMIN.name

    val isOwner = true // TODO Change this
    val testNumber = Random.nextInt(0, 10) // TODO Change this
    val cartItemCount = testNumber
    val isCartEmpty = cartItemCount == 0

    var isScrolledDown by remember { mutableStateOf(true) }

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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { ModalDrawerSheet {
            Column() {
                // TODO
            }
        } }
    ) {
        Scaffold(
            topBar = {
                AnimatedVisibility(
                    visible = isHome || isReport,
                    enter = slideInVertically(initialOffsetY = { -it }, animationSpec = tween(200, easing = LinearOutSlowInEasing)),
                    exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(200, easing = LinearOutSlowInEasing))
                ) {
                    Column {
                        SearchBar(
                            query = "",
                            onQueryChange = {},
                            onSearch = {},
                            active = isSearchActive,
                            onActiveChange = {
                                isSearchActive = it
                            },
                            placeholder = {
                                if (isHome) {
                                    Text(stringResource(R.string.productSearch))
                                } else if (isReport) {
                                    Text(stringResource(R.string.reportSearch))
                                }
                            },
                            leadingIcon = {
                                if (isSearchActive) {
                                    IconButton({ isSearchActive = false }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            stringResource(R.string.back)
                                        )
                                    }
                                } else {
                                    IconButton({ scope.launch { drawerState.open() } }) {
                                        Icon(
                                            Icons.Default.Menu,
                                            stringResource(R.string.menu)
                                        )
                                    }
                                }
                            },
                            trailingIcon = {
                                if (isSearchActive) {
                                    IconButton({ isSearchActive = false }) {
                                        Icon(
                                            Icons.Default.Search,
                                            stringResource(R.string.search)
                                        )
                                    }
                                } else {
                                    AnimatedVisibility(
                                        visible = isHome,
                                        enter = fadeIn(animationSpec = tween(200, easing = LinearOutSlowInEasing)),
                                        exit = fadeOut(animationSpec = tween(200, easing = LinearOutSlowInEasing))
                                    ) {
                                        BadgedBox(
                                            { if (!isCartEmpty) {
                                                Badge(
                                                    Modifier.offset(-Size.SIZE_3XL.dp, Size.SIZE_XS.dp)
                                                ){
                                                    Text(cartItemCount.toString())
                                                }
                                            } },
                                        ) {
                                            IconButton(
                                                { /* TODO */ }
                                            ) {
                                                Icon(
                                                    Icons.Default.ShoppingCart,
                                                    stringResource(R.string.cart)
                                                )
                                            }
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = animateDpAsState(
                                        label = stringResource(R.string.search),
                                        targetValue = if (isSearchActive) Size.SIZE_0.dp else Size.DEFAULT_SPACE.dp
                                    ).value
                                )
                                .padding(bottom = Size.BETWEEN_ITEMS.dp),
                        ) { }

                        var selectedChipIndex by remember { mutableIntStateOf(0) }
                        AnimatedVisibility(
                            visible = isScrolledDown && isHome,
                        ) {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(Size.BETWEEN_ITEMS.dp),
                                contentPadding = PaddingValues(horizontal = Size.DEFAULT_SPACE.dp)
                            ) {
                                itemsIndexed((0..3).toList()) { index, _ ->
                                    val isSelected = selectedChipIndex == index

                                    FilterChip(
                                        onClick = {
                                            selectedChipIndex = index
                                        },
                                        label = {
                                            Text(stringResource(R.string.placeholder))
                                        },
                                        selected = isSelected,
                                    )
                                }
                            }
                        }
                    }
                }
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = !isCartEmpty && isHome && !isSearchActive,
                    enter = slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }),
                    exit = slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth })
                ) {
                    FloatingActionButton(
                        { /*TODO*/ }
                    ) {
                        Icon(
                            Icons.Default.Add,
                            stringResource(R.string.add)
                        )
                    }
                }
            },
            bottomBar = {
                if (isOwner) {
                    AnimatedVisibility(
                        visible = !isSearchActive,
                        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(200, easing = LinearOutSlowInEasing)),
                        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(200, easing = LinearOutSlowInEasing))
                    ) {
                        NavigationBar(
                            Modifier
                                .fillMaxWidth()
                                .height(Size.NAV_BAR_HEIGHT.dp)
                        ) {
                            listOfNavigationItems(context).forEach { item ->
                                val isCurrentPage = currentDestination == item.route.name

                                NavigationBarItem(
                                    isCurrentPage,
                                    { if (!isCurrentPage) {
                                        navController.navigate(item.route.name) {
                                            popUpTo(0)
                                            launchSingleTop = true
                                        }
                                    } },
                                    { Icon(
                                        if (isCurrentPage) item.activeIcon else item.inactiveIcon,
                                        item.route.name,
                                        tint = MaterialTheme.colorScheme.primary
                                    ) },
                                    label = {
                                        Text(
                                            item.name,
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                    },
                                    alwaysShowLabel = isCurrentPage
                                )
                            }
                        }
                    }
                }
            },
        ) { innerPadding ->
            NavHost(
                navController,
                MainScreenState.HOME.name,
                enterTransition = { scaleIn(tween(200), 0.96f) + fadeIn(tween(200)) },
                exitTransition = { scaleOut(tween(200), 0.96f) + fadeOut(tween(200)) },
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(MainScreenState.HOME.name) {
                    Home(
                        nestedScrollConnection
                    )
                }
                composable(MainScreenState.REPORT.name) {
                    Report(
                        nestedScrollConnection
                    )
                }
                composable(MainScreenState.ADMIN.name) { Admin() }
            }
        }
    }
}