package com.babakan.cashier.presentation.navigation.screen.navigation

import Admin
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.babakan.cashier.common.audit.category.CategoryBottomSheet
import com.babakan.cashier.common.audit.product.ProductBottomSheet
import com.babakan.cashier.common.audit.user.UserBottomSheet
import com.babakan.cashier.data.sealed.AdminItem
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.authentication.viewmodel.AuthViewModel
import com.babakan.cashier.presentation.cashier.screen.cart.Cart
import com.babakan.cashier.presentation.cashier.screen.home.Home
import com.babakan.cashier.presentation.navigation.screen.navigation.component.bottombar.NavigationBottomBar
import com.babakan.cashier.presentation.navigation.screen.navigation.component.drawer.NavigationDrawer
import com.babakan.cashier.presentation.navigation.screen.navigation.component.fab.NavigationFab
import com.babakan.cashier.presentation.navigation.screen.navigation.component.topbar.NavigationTopBar
import com.babakan.cashier.presentation.owner.screen.transaction.Transaction
import com.babakan.cashier.presentation.cashier.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.utils.animation.Duration
import com.babakan.cashier.utils.animation.fadeInAnimation
import com.babakan.cashier.utils.animation.fadeOutAnimation
import com.babakan.cashier.utils.animation.scaleInAnimation
import com.babakan.cashier.utils.animation.scaleOutAnimation
import com.babakan.cashier.utils.constant.AuditState
import com.babakan.cashier.utils.constant.MainScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun MainNavigation(
    temporaryCartViewModel: TemporaryCartViewModel = viewModel(),
    authViewModel: AuthViewModel,
    authScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    onNavigateToLogin: () -> Unit
) {
    val currentUserState by authViewModel.currentUserState.collectAsState()
    var currentUser by remember { mutableStateOf(UserModel()) }

    LaunchedEffect(currentUserState) {
        if (currentUserState is UiState.Success) {
            currentUser = (currentUserState as UiState.Success<UserModel>).data
        }
    }

    val isOwner = currentUser.isOwner

    val mainScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val sheetState = rememberModalBottomSheetState()
    var auditSheetState by remember { mutableStateOf(AuditState.HIDDEN) }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    var isSearchActive by remember { mutableStateOf(false) }

    val tabs = listOf(R.string.menu, R.string.category, R.string.user)
    var selectedAdminTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(selectedAdminTabIndex, pageCount = { tabs.size })
    val onSelectedAdminTabIndex: (Int) -> Unit = { index ->
        mainScope.launch {
            pagerState.scrollToPage(index)
        }
        selectedAdminTabIndex = index
    }

    val isMENU = currentDestination == MainScreenState.MENU.name
    val isTransaction = currentDestination == MainScreenState.REPORT.name
    val isAdmin = currentDestination == MainScreenState.ADMIN.name
    val isCart = currentDestination == MainScreenState.CART.name
    val isAdminProduct = pagerState.currentPage == 0
    val isAdminCategory = pagerState.currentPage == 1
    val isAdminUser = pagerState.currentPage == 2

    var isScrolledDown by remember { mutableStateOf(false) }
    if (isMENU || isTransaction || isAdmin) {
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

    var selectedAuditItem by remember { mutableStateOf<AdminItem?>(null) }
    var isAddNewItem by remember { mutableStateOf(false) }

    val temporaryTotalQuantity by temporaryCartViewModel.temporaryTotalQuantity.collectAsState()
    val isTemporaryProductEmpty = temporaryTotalQuantity == 0
    val isFabShown = !isSearchActive && ((isMENU && !isTemporaryProductEmpty) || isAdmin)
    if (isTransaction || isAdmin) {
        temporaryCartViewModel.clearTemporaryCart()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = currentDestination != MainScreenState.CART.name && !isSearchActive,
        drawerContent = {
            NavigationDrawer(
                authViewModel = authViewModel,
                currentUser = currentUser,
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
                    temporaryCartViewModel = temporaryCartViewModel,
                    isHome = isMENU,
                    isReport = isTransaction,
                    isAdmin = isAdmin,
                    isCart = isCart,
                    isAdminProduct = isAdminProduct,
                    isAdminCategory = isAdminCategory,
                    isAdminUser = isAdminUser,
                    isSearchActive = isSearchActive,
                    snackBarHostState = snackBarHostState,
                    onSearchActiveChange = { isSearchActive = it },
                    drawerState = drawerState,
                    scope = mainScope,
                    nestedScrollConnection = nestedScrollConnection,
                    isScrolledDown = isScrolledDown,
                    pagerState = pagerState,
                    tabs = tabs,
                    navController = navController,
                    onAuditStateChange = { auditSheetState = it },
                    onItemSelected = { item ->
                        selectedAuditItem = item
                        auditSheetState = when (item) {
                            is AdminItem.Product -> AuditState.PRODUCT
                            is AdminItem.Category -> AuditState.CATEGORY
                            is AdminItem.User -> AuditState.USER
                        }
                    },
                )
            },
            floatingActionButton = {
                NavigationFab(
                    temporaryCartViewModel = temporaryCartViewModel,
                    temporaryTotalQuantity = temporaryTotalQuantity,
                    isHome = isMENU,
                    isAdminProduct = isAdminProduct,
                    isAdminCategory = isAdminCategory,
                    isAdminUser = isAdminUser,
                    isFabShown = isFabShown,
                    isScrolledDown = isScrolledDown,
                    mainScope = mainScope,
                    snackBarHostState = snackBarHostState,
                    onDrawerStateChange = { DrawerValue.Open },
                    onSelectedAuditItemChange = { selectedAuditItem = it },
                    onAuditSheetStateChange = { auditSheetState = it },
                    onAddNewItemChange = { isAddNewItem = true }
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
                MainScreenState.MENU.name,
                enterTransition = { scaleInAnimation(Duration.ANIMATION_MEDIUM) + fadeInAnimation(Duration.ANIMATION_MEDIUM) },
                exitTransition = { scaleOutAnimation(Duration.ANIMATION_MEDIUM) + fadeOutAnimation(Duration.ANIMATION_MEDIUM) },
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(MainScreenState.MENU.name) {
                    Home(
                        temporaryCartViewModel,
                        nestedScrollConnection,
                        isFabShown
                    )
                }
                composable(MainScreenState.REPORT.name) {
                    Transaction(
                        nestedScrollConnection
                    )
                }
                composable(MainScreenState.ADMIN.name) {
                    Admin(
                        nestedScrollConnection = nestedScrollConnection,
                        pagerState = pagerState,
                        onSelectedAdminTabIndex = onSelectedAdminTabIndex,
                        onAuditStateChange = { auditSheetState = it },
                        onItemSelected = { item ->
                            selectedAuditItem = item
                            auditSheetState = when (item) {
                                is AdminItem.Product -> AuditState.PRODUCT
                                is AdminItem.Category -> AuditState.CATEGORY
                                is AdminItem.User -> AuditState.USER
                            }
                        },
                    )
                }
                composable(MainScreenState.CART.name) {
                    Cart(
                        nestedScrollConnection = nestedScrollConnection,
                        snackBarHostState = snackBarHostState,
                        isScrolledDown = isScrolledDown,
                        onCartConfirmClick = {
                            // TODO: Create Transaction
                        }
                    )
                }
            }
            when (auditSheetState) {
                AuditState.PRODUCT -> {
                    ProductBottomSheet(
                        scope = mainScope,
                        snackBarHostState = snackBarHostState,
                        sheetState = sheetState,
                        item = (selectedAuditItem as AdminItem.Product).product,
                        onDismiss = { auditSheetState = AuditState.HIDDEN },
                        isAddNew = isAddNewItem,
                    )
                }
                AuditState.CATEGORY -> {
                    CategoryBottomSheet(
                        scope = mainScope,
                        snackBarHostState = snackBarHostState,
                        sheetState = sheetState,
                        item = (selectedAuditItem as AdminItem.Category).category,
                        onDismiss = { auditSheetState = AuditState.HIDDEN },
                        isAddNew = isAddNewItem,
                    )
                }
                AuditState.USER -> {
                    UserBottomSheet(
                        scope = mainScope,
                        snackBarHostState = snackBarHostState,
                        sheetState = sheetState,
                        item = (selectedAuditItem as AdminItem.User).user,
                        onDismiss = { auditSheetState = AuditState.HIDDEN },
                        isAddNew = isAddNewItem,
                    )
                }
                else -> {
                    auditSheetState = AuditState.HIDDEN
                    isAddNewItem = false
                }
            }
        }
    }
}