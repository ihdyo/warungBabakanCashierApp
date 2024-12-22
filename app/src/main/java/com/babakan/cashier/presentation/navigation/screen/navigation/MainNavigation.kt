package com.babakan.cashier.presentation.navigation.screen.navigation

import android.util.Log
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
import com.babakan.cashier.presentation.cashier.viewmodel.CartViewModel
import com.babakan.cashier.presentation.navigation.screen.navigation.component.bottombar.NavigationBottomBar
import com.babakan.cashier.presentation.navigation.screen.navigation.component.drawer.NavigationDrawer
import com.babakan.cashier.presentation.navigation.screen.navigation.component.fab.NavigationFab
import com.babakan.cashier.presentation.navigation.screen.navigation.component.topbar.NavigationTopBar
import com.babakan.cashier.presentation.owner.screen.transaction.Transaction
import com.babakan.cashier.presentation.cashier.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.babakan.cashier.presentation.owner.model.TransactionModel
import com.babakan.cashier.presentation.owner.screen.admin.Admin
import com.babakan.cashier.presentation.owner.viewmodel.CategoryViewModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductOutViewModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductViewModel
import com.babakan.cashier.presentation.owner.viewmodel.TransactionViewModel
import com.babakan.cashier.presentation.owner.viewmodel.UserViewModel
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
    productViewModel: ProductViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(),
    temporaryCartViewModel: TemporaryCartViewModel = viewModel(),
    authViewModel: AuthViewModel,
    authScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    onNavigateToLogin: () -> Unit
) {
    val currentUserState by authViewModel.currentUserState.collectAsState()
    val productsState by productViewModel.fetchProductsState.collectAsState()
    val productByCategoryState by productViewModel.searchProductByCategoryState.collectAsState()
    val categoryState by categoryViewModel.fetchCategoriesState.collectAsState()
    val cartState by cartViewModel.fetchCartState.collectAsState()
    val usersState by userViewModel.fetchUsersState.collectAsState()

    var currentUser by remember { mutableStateOf(UserModel()) }
    var products by remember { mutableStateOf(emptyList<ProductModel>()) }
    var categories by remember { mutableStateOf(emptyList<CategoryModel>()) }
    var cart by remember { mutableStateOf(emptyList<ProductOutModel>()) }
    var users by remember { mutableStateOf(emptyList<UserModel>()) }

    var productTriggerEvent by remember { mutableStateOf(false) }
    var categoryTriggerEvent by remember { mutableStateOf(false) }
    var cartTriggerEvent by remember { mutableStateOf(false) }
    var userTriggerEvent by remember { mutableStateOf(false) }

    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(currentUserState) {
        if (currentUserState is UiState.Loading) {
            showLoading = true
        } else if (currentUserState is UiState.Success) {
            showLoading = false
            currentUser = (currentUserState as UiState.Success<UserModel>).data
        }
    }
    LaunchedEffect(productsState, productTriggerEvent) {
        if (productTriggerEvent) {
            productViewModel.fetchProducts()
            productTriggerEvent = false
        }
        if (productsState is UiState.Loading) {
            showLoading = true
        } else if (productsState is UiState.Success) {
            showLoading = false
            products = (productsState as UiState.Success<List<ProductModel>>).data
        }
    }
    LaunchedEffect(productByCategoryState) {
        if (productByCategoryState is UiState.Loading) {
            showLoading = true
        } else if (productByCategoryState is UiState.Success) {
            showLoading = false
            products = (productByCategoryState as UiState.Success<List<ProductModel>>).data
        }
    }
    LaunchedEffect(categoryState, categoryTriggerEvent) {
        if (categoryTriggerEvent) {
            categoryViewModel.fetchCategories()
            categoryTriggerEvent = false
        }
        if (categoryState is UiState.Loading) {
            showLoading = true
        } else if (categoryState is UiState.Success) {
            showLoading = false
            categories = (categoryState as UiState.Success<List<CategoryModel>>).data
        }
    }
    LaunchedEffect(cartState, cartTriggerEvent) {
        if (cartTriggerEvent) {
            cartViewModel.fetchCart()
            cartTriggerEvent = false
        }
        if (cartState is UiState.Loading) {
            showLoading = true
        } else if (cartState is UiState.Success) {
            showLoading = false
            cart = (cartState as UiState.Success<List<ProductOutModel>>).data
        }
    }
    LaunchedEffect(usersState, userTriggerEvent) {
        if (userTriggerEvent) {
            userViewModel.fetchUsers()
            userTriggerEvent = false
        }
        if (usersState is UiState.Loading) {
            showLoading = true
        } else if (usersState is UiState.Success) {
            showLoading = false
            users = (usersState as UiState.Success<List<UserModel>>).data
        }
    }

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

    val isMenu = currentDestination == MainScreenState.MENU.name
    val isTransaction = currentDestination == MainScreenState.REPORT.name
    val isAdmin = currentDestination == MainScreenState.ADMIN.name
    val isCart = currentDestination == MainScreenState.CART.name
    val isAdminProduct = pagerState.currentPage == 0
    val isAdminCategory = pagerState.currentPage == 1
    val isAdminUser = pagerState.currentPage == 2

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

    var selectedAuditItem by remember { mutableStateOf<AdminItem?>(null) }
    var isAddNewItem by remember { mutableStateOf(false) }

    val temporaryTotalQuantity by temporaryCartViewModel.temporaryTotalQuantity.collectAsState()
    val temporaryItem by temporaryCartViewModel.temporaryCartProduct.collectAsState()

    val isTemporaryProductEmpty = temporaryTotalQuantity == 0
    val isFabShown = !isSearchActive && ((isMenu && !isTemporaryProductEmpty) || isAdmin)
    if (isTransaction || isAdmin) {
        temporaryCartViewModel.clearTemporaryCart()
    }

    val isOwner = currentUser.isOwner

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
                    cartViewModel = cartViewModel,
                    temporaryCartViewModel = temporaryCartViewModel,
                    cart = cart,
                    categories = categories,
                    onCategorySelected = {
                        productViewModel.searchProductByCategory(it.id, false)
                    },
                    onAllCategorySelected = {
                        productViewModel.searchProductByCategory("", true)
                    },
                    isHome = isMenu,
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
                    triggerEvent = { cartTriggerEvent = it }
                )
            },
            floatingActionButton = {
                NavigationFab(
                    temporaryCartViewModel = temporaryCartViewModel,
                    temporaryItem = temporaryItem,
                    scope = mainScope,
                    snackBarHostState = snackBarHostState,
                    isHome = isMenu,
                    isAdminProduct = isAdminProduct,
                    isAdminCategory = isAdminCategory,
                    isAdminUser = isAdminUser,
                    isFabShown = isFabShown,
                    isScrolledDown = isScrolledDown,
                    onSelectedAuditItemChange = { selectedAuditItem = it },
                    onAuditSheetStateChange = { auditSheetState = it },
                    onAddNewItemChange = { isAddNewItem = true },
                    triggerEvent = { cartTriggerEvent = it }
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
                        products = products,
                        categories = categories,
                        temporaryCartViewModel = temporaryCartViewModel,
                        nestedScrollConnection = nestedScrollConnection,
                        isFabShown = isFabShown,
                        showLoading = showLoading
                    )
                }
                composable(MainScreenState.REPORT.name) {
                    Transaction(
                        userViewModel = userViewModel,
                        nestedScrollConnection = nestedScrollConnection
                    )
                }
                composable(MainScreenState.ADMIN.name) {
                    Admin(
                        productViewModel = productViewModel,
                        categoryViewModel = categoryViewModel,
                        userViewModel = userViewModel,
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
                        }
                    )
                }
                composable(MainScreenState.CART.name) {
                    Cart(
                        cartViewModel = cartViewModel,
                        cart = cart,
                        nestedScrollConnection = nestedScrollConnection,
                        snackBarHostState = snackBarHostState,
                        isScrolledDown = isScrolledDown,
                        cartTriggerEvent = { cartTriggerEvent = it }
                    )
                }
            }
            when (auditSheetState) {
                AuditState.PRODUCT -> {
                    ProductBottomSheet(
                        productViewModel = productViewModel,
                        scope = mainScope,
                        snackBarHostState = snackBarHostState,
                        sheetState = sheetState,
                        item = (selectedAuditItem as AdminItem.Product).product,
                        categories = categories,
                        onDismiss = { auditSheetState = AuditState.HIDDEN },
                        isAddNew = isAddNewItem,
                        triggerEvent = { productTriggerEvent = it }
                    )
                }
                AuditState.CATEGORY -> {
                    CategoryBottomSheet(
                        categoryViewModel = categoryViewModel,
                        scope = mainScope,
                        snackBarHostState = snackBarHostState,
                        sheetState = sheetState,
                        item = (selectedAuditItem as AdminItem.Category).category,
                        onDismiss = { auditSheetState = AuditState.HIDDEN },
                        isAddNew = isAddNewItem,
                        triggerEvent = { categoryTriggerEvent = it }
                    )
                }
                AuditState.USER -> {
                    UserBottomSheet(
                        userViewModel = userViewModel,
                        scope = mainScope,
                        snackBarHostState = snackBarHostState,
                        sheetState = sheetState,
                        item = (selectedAuditItem as AdminItem.User).user,
                        onDismiss = { auditSheetState = AuditState.HIDDEN },
                        isAddNew = isAddNewItem,
                        triggerEvent = { userTriggerEvent = it }
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