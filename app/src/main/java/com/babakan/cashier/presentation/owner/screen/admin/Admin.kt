import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import com.babakan.cashier.R
import com.babakan.cashier.presentation.owner.screen.admin.cashier.AdminCashier
import com.babakan.cashier.presentation.owner.screen.admin.category.AdminCategory
import com.babakan.cashier.presentation.owner.screen.admin.product.AdminProduct
import kotlinx.coroutines.CoroutineScope

@Composable
fun Admin(
    nestedScrollConnection: NestedScrollConnection,
    pagerState: PagerState,
    onSelectedAdminTabIndex: (Int) -> Unit
) {
    LaunchedEffect(pagerState.currentPage) {
        onSelectedAdminTabIndex(pagerState.currentPage)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top
    ) {
        HorizontalPager(
            pagerState,
            Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> AdminProduct(nestedScrollConnection)
                1 -> AdminCategory(nestedScrollConnection)
                2 -> AdminCashier(nestedScrollConnection)
            }
        }
    }
}