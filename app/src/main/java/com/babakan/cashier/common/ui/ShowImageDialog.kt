package com.babakan.cashier.common.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.babakan.cashier.utils.builder.ImageLoader
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun ShowImageDialog(
    imageUrl: String,
    onDismiss: () -> Unit,
) {
    Dialog({ onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SizeChart.DEFAULT_SPACE.dp),
            shape = MaterialTheme.shapes.large,
        ) {
            ImageLoader(
                imageUrl,
                SizeChart.IMAGE_EXPANDED_HEIGHT.dp
            )
        }
    }
}