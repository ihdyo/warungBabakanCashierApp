package com.babakan.cashier.utils.builder

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.babakan.cashier.R

@Composable
fun ImageLoader(
    imageUrl: String,
    height: Dp,
    width: Dp = Dp.Unspecified
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        error = painterResource(R.drawable.img_placeholder),
        contentDescription = stringResource(R.string.menu),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(height)
            .width(width)
    )
}