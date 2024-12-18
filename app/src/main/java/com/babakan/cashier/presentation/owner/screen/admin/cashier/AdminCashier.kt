package com.babakan.cashier.presentation.owner.screen.admin.cashier

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.common.component.EditButton
import com.babakan.cashier.common.style.tabContentPadding
import com.babakan.cashier.utils.constant.SizeChart
import kotlin.random.Random

@Composable
fun AdminCashier(nestedScrollConnection: NestedScrollConnection) {
    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = tabContentPadding(),
            verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .nestedScroll(nestedScrollConnection)
        ) {
            items(6) {
                val isActive = Random.nextBoolean()
                val isOwner = Random.nextBoolean()

                Card(colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer)) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
                        modifier = Modifier.padding(SizeChart.SMALL_SPACE.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "lorem_ipsum",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            EditButton {
                                // TODO Edit
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    "Lorem Ipsum",
                                    style = MaterialTheme.typography.titleLarge,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Text(
                                    "loremipsum@gmail.com",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp)
                            ) {
                                if (isOwner) {
                                    Icon(
                                        Icons.Default.Verified,
                                        stringResource(R.string.edit),
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(SizeChart.ICON_MEDIUM.dp)
                                    )
                                }
                                Text(
                                    if (isOwner) stringResource(R.string.owner) else stringResource(R.string.cashier),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Card(
                                    shape = MaterialTheme.shapes.extraSmall,
                                    colors = CardDefaults.cardColors(if (isActive) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer)
                                ) {
                                    Icon(
                                        if (isActive) Icons.Default.Check else Icons.Default.Close,
                                        stringResource(R.string.status),
                                        tint = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                        modifier = Modifier
                                            .padding(SizeChart.SIZE_2XS.dp)
                                            .size(SizeChart.ICON_SMALL.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}