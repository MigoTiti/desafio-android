package com.lucasrodrigues.apodnasa.extension

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import com.lucasrodrigues.apodnasa.ui.routing.Route

fun NavController.navigate(
    route: Route,
    args: List<Any> = emptyList(),
    builder: NavOptionsBuilder.() -> Unit = {},
) {
    val destinationRoute = StringBuilder(route.nameNoArgs).apply {
        args.forEach {
            append("/$it")
        }
    }.toString()

    this.navigate(destinationRoute, builder)
}

fun NavGraphBuilder.routeComposable(
    route: Route,
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = route.fullName,
        arguments = route.namedNavArguments,
        deepLinks = deepLinks,
        content = content,
    )
}