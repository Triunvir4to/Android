package com.example.newsapp.services.navigation

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.io.Serializable
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class CustomNavType<T : Serializable>(
    clazz: Class<T>,
    private val serializer: KSerializer<T>,
) : NavType<T>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): T? =
        bundle.getSerializable(key) as? T

    override fun put(bundle: Bundle, key: String, value: T) =
        bundle.putSerializable(key, value)

    override fun parseValue(value: String): T = Json.decodeFromString(serializer, value)

    override fun serializeAsValue(value: T): String = Json.encodeToString(serializer, value)

    override val name: String = clazz.name

    companion object {
        inline fun <reified T : Serializable> getCustomNavTypeMap(serializer: KSerializer<T>): Map<KType, CustomNavType<T>> =
            mapOf(
                typeOf<T>() to CustomNavType(T::class.java, serializer),
            )
    }
}
