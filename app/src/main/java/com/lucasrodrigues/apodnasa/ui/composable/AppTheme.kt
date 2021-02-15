package com.lucasrodrigues.apodnasa.ui.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.lucasrodrigues.apodnasa.R

@Composable
fun AppTheme(
    dark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (dark)
            Colors(
                primary = colorResource(id = R.color.primary),
                primaryVariant = colorResource(id = R.color.primaryVariant),
                secondary = colorResource(id = R.color.secondary),
                secondaryVariant = colorResource(id = R.color.secondaryVariant),
                background = Color.Black,
                surface = colorResource(id = R.color.darkGray),
                error = colorResource(id = R.color.secondary),
                onPrimary = Color.Black,
                onSecondary = Color.Black,
                onError = Color.Black,
                onBackground = Color.White,
                onSurface = Color.White,
                isLight = false,
            )
        else
            Colors(
                primary = colorResource(id = R.color.primary),
                primaryVariant = colorResource(id = R.color.primaryVariant),
                secondary = colorResource(id = R.color.secondary),
                secondaryVariant = colorResource(id = R.color.secondaryVariant),
                background = Color.White,
                surface = Color.White,
                error = colorResource(id = R.color.secondary),
                onPrimary = Color.White,
                onSecondary = Color.White,
                onError = Color.White,
                onBackground = Color.Black,
                onSurface = Color.Black,
                isLight = true,
            ),
        content = content,
    )
}