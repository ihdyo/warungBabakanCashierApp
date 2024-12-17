package com.babakan.cashier.presentation.navigation.screen.drawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.SizeChart
import kotlin.random.Random

@Composable
fun MainDrawer() {

    // TODO Change this
    val name = stringResource(R.string.placeholder)
    val isOwner = Random.nextBoolean()

    val onLogout: () -> Unit = {}

    ModalDrawerSheet {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .padding(SizeChart.DEFAULT_SPACE.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(SizeChart.DEFAULT_SPACE.dp),
                ) {
                    Card {
                        Icon(
                            if (isOwner) Icons.Default.Verified else Icons.Default.Store,
                            stringResource(R.string.cashier),
                            modifier = Modifier.padding(SizeChart.SMALL_SPACE.dp)
                        )
                    }
                    Column {
                        Text(
                            stringResource(R.string.greeting),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            name,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                HorizontalDivider(Modifier.padding(vertical = SizeChart.DEFAULT_SPACE.dp))
                Text(
                    stringResource(R.string.brandName),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            TextButton(
                { onLogout() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.logout),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}