package com.babakan.cashier.presentation.cashier.screen.invoice

import android.graphics.Picture
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.R
import com.babakan.cashier.common.item.InvoiceItem
import com.babakan.cashier.common.ui.FullscreenLoading
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.owner.model.TransactionModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductOutViewModel
import com.babakan.cashier.presentation.owner.viewmodel.TransactionViewModel
import com.babakan.cashier.presentation.owner.viewmodel.UserViewModel
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun Invoice(
    userViewModel: UserViewModel = viewModel(),
    transactionViewModel: TransactionViewModel = viewModel(),
    productOutViewModel: ProductOutViewModel = viewModel(),
    transactionId: String,
    picture: Picture
) {
    val scrollState = rememberScrollState()
    val userByIdState by userViewModel.fetchUserByIdState.collectAsState()
    val transactionByIdState by transactionViewModel.transactionByIdState.collectAsState()
    val productOutState by productOutViewModel.fetchProductOutState.collectAsState()
    var contentHeight by remember { mutableStateOf(0) }

    LaunchedEffect(transactionId) {
        transactionViewModel.fetchTransactionById(transactionId)
        productOutViewModel.fetchProductOut(transactionId)
    }

    val transaction = when (val state = transactionByIdState) {
        is UiState.Success -> state.data
        else -> TransactionModel()
    }

    val productOut = when (val state = productOutState) {
        is UiState.Success -> state.data
        else -> emptyList()
    }

    val userId = transaction.userId

    LaunchedEffect(userId) {
        userViewModel.fetchUserById(userId)
    }

    val user = when (val state = userByIdState) {
        is UiState.Success -> state.data
        else -> UserModel()
    }

    val showLoading = userByIdState is UiState.Loading || transactionByIdState is UiState.Loading || productOutState is UiState.Loading

    Box(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .verticalScroll(scrollState)
                .onGloballyPositioned { coordinates ->
                    contentHeight = coordinates.size.height
                }
                .drawWithCache {
                    val width = this.size.width.toInt()

                    val height = contentHeight

                    onDrawWithContent {
                        val pictureCanvas = Canvas(
                            picture.beginRecording(width, height)
                        )
                        draw(this, this.layoutDirection, pictureCanvas, this.size) {
                            this@onDrawWithContent.drawContent()
                        }
                        picture.endRecording()

                        drawIntoCanvas { canvas ->
                            canvas.nativeCanvas.drawPicture(picture)
                        }
                    }
                }
        ) {
            Column(
                Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = SizeChart.DEFAULT_SPACE.dp)
                    .padding(top = SizeChart.BETWEEN_SECTIONS.dp)
            ) {
                if (showLoading) { FullscreenLoading() }
                Text(
                    stringResource(R.string.invoice),
                    style = MaterialTheme.typography.titleLarge
                )
                InvoiceItem(
                    transactionItem = transaction,
                    userItem = user,
                    productOut = productOut,
                )
            }
        }
    }
}