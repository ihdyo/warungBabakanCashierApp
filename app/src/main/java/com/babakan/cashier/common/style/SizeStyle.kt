package com.babakan.cashier.common.style

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import com.babakan.cashier.utils.constant.SizeChart

fun pageContentPadding(
    isFabShown: Boolean
): PaddingValues {
    return PaddingValues(
        top = SizeChart.SMALL_SPACE.dp,
        start = SizeChart.DEFAULT_SPACE.dp,
        end = SizeChart.DEFAULT_SPACE.dp,
        bottom = if (isFabShown) SizeChart.DUAL_FAB_HEIGHT.dp else SizeChart.DEFAULT_SPACE.dp,
    )
}

fun tabContentPadding(): PaddingValues {
    return PaddingValues(
        top = SizeChart.DEFAULT_SPACE.dp,
        start = SizeChart.DEFAULT_SPACE.dp,
        end = SizeChart.DEFAULT_SPACE.dp,
        bottom = SizeChart.FAB_HEIGHT.dp
    )
}