package com.babakan.cashier.utils.animation

import androidx.compose.animation.*
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.animation.core.*

object Duration {
    const val ANIMATION_SHORT = 200
    const val ANIMATION_MEDIUM = 300
    const val ANIMATION_LONG = 500
}

// Fade in
fun fadeInAnimation(duration: Int): EnterTransition {
    return fadeIn(animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing))
}

// Fade out
fun fadeOutAnimation(duration: Int): ExitTransition {
    return fadeOut(animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing))
}

// Slide in to the right
fun slideInRightAnimation(duration: Int): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing)
    )
}

// Slide in to the left
fun slideInLeftAnimation(duration: Int): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing)
    )
}

// Slide out to the right
fun slideOutRightAnimation(duration: Int): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing)
    )
}

// Slide out to the left
fun slideOutLeftAnimation(duration: Int): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing)
    )
}

// Slide in to top
fun slideInTopAnimation(duration: Int): EnterTransition {
    return slideInVertically(
        initialOffsetY = { fullWidth -> fullWidth },
        animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing)
    )
}

// Slide in to bottom
fun slideInBottomAnimation(duration: Int): EnterTransition {
    return slideInVertically(
        initialOffsetY = { fullWidth -> -fullWidth },
        animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing)
    )
}

// Slide out to bottom
fun slideOutBottomAnimation(duration: Int): ExitTransition {
    return slideOutVertically(
        targetOffsetY = { fullWidth -> fullWidth },
        animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing)
    )
}

// Slide out to top
fun slideOutTopAnimation(duration: Int): ExitTransition {
    return slideOutVertically(
        targetOffsetY = { fullWidth -> -fullWidth },
        animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing)
    )
}

// Scale in
fun scaleInAnimation(duration: Int): EnterTransition {
    return scaleIn(
        animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing),
        initialScale = 0.96f,
        transformOrigin = TransformOrigin.Center
    )
}

// Scale out
fun scaleOutAnimation(duration: Int): ExitTransition {
    return scaleOut(
        animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing),
        targetScale = 0.96f,
        transformOrigin = TransformOrigin.Center
    )
}