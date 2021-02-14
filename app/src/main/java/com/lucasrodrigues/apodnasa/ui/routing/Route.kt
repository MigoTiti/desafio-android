package com.lucasrodrigues.apodnasa.ui.routing

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument
import kotlin.reflect.KClass

enum class Route(
    val nameNoArgs: String,
    val args: LinkedHashMap<String, KClass<*>> = linkedMapOf(),
) {
    HOME(
        nameNoArgs = "home",
    ),
    APOD_DETAILS(
        nameNoArgs = "apod_details",
        args = linkedMapOf(
            "apodTimestamp" to Long::class,
        ),
    );

    val namedNavArguments: List<NamedNavArgument>
        get() {
            return args.map {
                navArgument(it.key) {
                    type = when (it.value) {
                        Long::class -> NavType.LongType
                        Int::class -> NavType.IntType
                        Float::class -> NavType.FloatType
                        String::class -> NavType.StringType
                        Boolean::class -> NavType.BoolType
                        else -> throw IllegalArgumentException("Type not supported")
                    }
                }
            }
        }

    val fullName: String
        get() {
            return StringBuilder(nameNoArgs).apply {
                args.forEach {
                    append("/{${it.key}}")
                }
            }.toString()
        }
}