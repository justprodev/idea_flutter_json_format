package com.justprodev.dart_json_generator.utils


import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.PropertyKey
import java.util.function.Supplier

@Nls
private const val BUNDLE = "messages.LabelsBundle"

internal object LabelsBundle {
    private val INSTANCE = object : DynamicBundle(BUNDLE){}

    fun message(
        key: @PropertyKey(resourceBundle = BUNDLE) String,
        vararg params: Any
    ): @Nls String {
        return INSTANCE.getMessage(key, *params)
    }

    fun lazyMessage(
        @PropertyKey(resourceBundle = BUNDLE) key: String,
        vararg params: Any
    ): Supplier<@Nls String> {
        return INSTANCE.getLazyMessage(key, *params)
    }
}

fun getString(key: String, vararg params: Any): String {
    return LabelsBundle.message(key, *params)
}