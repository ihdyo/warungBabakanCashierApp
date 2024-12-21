package com.babakan.cashier.common.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.babakan.cashier.utils.builder.IconLoader
import com.babakan.cashier.common.component.EditButton
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun CategoryItem(
    index: Int,
    categoryItem: CategoryModel,
    isAdmin: Boolean,
    onAdminEdit: (CategoryModel) -> Unit
) {
    Card(colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(SizeChart.SMALL_SPACE.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp)
            ) {
                Card(
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Box(Modifier.padding(SizeChart.SMALL_SPACE.dp)) {
                        IconLoader(
                            categoryItem.iconUrl,
                            SizeChart.ICON_EXTRA_LARGE.dp,
                            MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
                Text(
                    categoryItem.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (isAdmin) { EditButton { onAdminEdit(categoryItem) } }
        }
    }
}