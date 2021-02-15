package com.lucasrodrigues.apodnasa.ui.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
    dark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (dark)
            Colors(
                primary = Color(0xFF0B3D91),
                primaryVariant = Color(0xFF001862),
                secondary = Color(0xFFFC3D21),
                secondaryVariant = Color(0xFF8D2415),
                background = Color(0xFF000000),
                surface = Color(0xFF1F2021),
                error = Color(0xFFFC3D21),
                onPrimary = Color(0xFF000000),
                onSecondary = Color(0xFF000000),
                onError = Color(0xFF000000),
                onBackground = Color(0xFFFFFFFF),
                onSurface = Color(0xFFFFFFFF),
                isLight = false,
            )
        else
            Colors(
                primary = Color(0xFF0B3D91),
                primaryVariant = Color(0xFF001862),
                secondary = Color(0xFFFC3D21),
                secondaryVariant = Color(0xFF8D2415),
                background = Color(0xFFFFFFFF),
                surface = Color(0xFFFFFFFF),
                error = Color(0xFFFC3D21),
                onPrimary = Color(0xFFFFFFFF),
                onSecondary = Color(0xFFFFFFFF),
                onError = Color(0xFFFFFFFF),
                onBackground = Color(0xFF000000),
                onSurface = Color(0xFF000000),
                isLight = true,
            ),
        content = content,
    )
}