package com.babakan.cashier.presentation.owner.screen.admin.product

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.overscroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.common.component.CategoryComponent
import com.babakan.cashier.common.component.EditButton
import com.babakan.cashier.utils.constant.Size
import com.babakan.cashier.utils.formatter.Formatter

@Composable
fun AdminProduct(
    nestedScrollConnection: NestedScrollConnection
) {
    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(Size.DEFAULT_SPACE.dp),
            verticalArrangement = Arrangement.spacedBy(Size.BETWEEN_ITEMS.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .nestedScroll(nestedScrollConnection)
        ) {
            items(6) {
                Card(colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer)) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Size.SMALL_SPACE.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(Size.SMALL_SPACE.dp),
                            modifier = Modifier.weight(1f).height(IntrinsicSize.Min)
                        ) {
                            Card(
                                shape = MaterialTheme.shapes.small
                            ) {
                                Image(
                                    painterResource(R.drawable.img_placeholder),
                                    stringResource(R.string.productImage),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(Size.IMAGE_ADMIN_HEIGHT.dp)
                                        .fillMaxHeight()
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                            ) {
                                CategoryComponent()
                                Text(
                                    "Lorem Ipsum",
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    Formatter.currency(10000),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                        }
                        EditButton {
                            // TODO Edit
                        }
                    }
                }
            }
        }
    }
}