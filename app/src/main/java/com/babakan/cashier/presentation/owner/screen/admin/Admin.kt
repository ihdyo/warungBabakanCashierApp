import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.babakan.cashier.data.sealed.AdminItem
import com.babakan.cashier.presentation.owner.screen.admin.user.AdminUser
import com.babakan.cashier.presentation.owner.screen.admin.category.AdminCategory
import com.babakan.cashier.presentation.owner.screen.admin.product.AdminProduct
import com.babakan.cashier.utils.constant.AuditState

@ExperimentalMaterial3Api
@Composable
fun Admin(
    nestedScrollConnection: NestedScrollConnection,
    pagerState: PagerState,
    onSelectedAdminTabIndex: (Int) -> Unit,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (AdminItem) -> Unit
) {

    LaunchedEffect(pagerState.currentPage) {
        onSelectedAdminTabIndex(pagerState.currentPage)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection),
        verticalArrangement = Arrangement.Top
    ) {
        HorizontalPager(
            pagerState,
            Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> AdminProduct(
                    nestedScrollConnection = nestedScrollConnection,
                    onAuditStateChange = onAuditStateChange,
                    onItemSelected = { item ->
                        onItemSelected(AdminItem.Product(item))
                    }
                )
                1 -> AdminCategory(
                    nestedScrollConnection = nestedScrollConnection,
                    onAuditStateChange = onAuditStateChange,
                    onItemSelected = { item ->
                        onItemSelected(AdminItem.Category(item))
                    }
                )
                2 -> AdminUser(
                    nestedScrollConnection = nestedScrollConnection,
                    onAuditStateChange = onAuditStateChange,
                    onItemSelected = { item ->
                        onItemSelected(AdminItem.User(item))
                    }
                )
            }
        }
    }
}