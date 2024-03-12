package com.projectdelta.jim.ui.common

import androidx.compose.animation.AnimatedContentTransitionScope
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
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400),
                    initialOffset = { it }
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400),
                    targetOffset = { it }
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400),
                    initialOffset = { it }
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400),
                    targetOffset = { it }
                )
            },
        )
    }
}
