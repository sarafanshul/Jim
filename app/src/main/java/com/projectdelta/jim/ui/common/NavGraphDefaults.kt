package com.projectdelta.jim.ui.common

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations

object NavGraphDefaults {
    /**
     * Basic Slide In/Out Animation.
     */
    val DEFAULT_ANIMATION by lazy {
        RootNavGraphDefaultAnimations(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(400),
                    initialOffset = { it }
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(400),
                    targetOffset = { it }
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(400),
                    initialOffset = { it }
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(400),
                    targetOffset = { it }
                )
            },
        )
    }
}
