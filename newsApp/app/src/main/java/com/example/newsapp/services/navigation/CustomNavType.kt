package com.example.newsapp.services.navigation

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.io.Serializable
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * A custom [NavType] implementation that supports [Serializable] objects.
 *
 * @param T The type of the [Serializable] object.
 * @param clazz The [Class] object of the [Serializable] type.
 * @param serializer The [KSerializer] for the [Serializable] type.
 */
class CustomNavType<T : Serializable>(
    clazz: Class<T>,
    private val serializer: KSerializer<T>,
) : NavType<T>(isNullableAllowed = false) {

    /**
     * Retrieves the [Serializable] object from the [Bundle] for the given [key] using JSON deserialization.
     *
     * @param bundle The [Bundle] containing the serialized [Serializable] object.
     * @param key The key associated with the serialized [Serializable] object.
     * @return The deserialized [Serializable] object, or null if not found.
     */
    override fun get(bundle: Bundle, key: String): T? {
        val jsonString = bundle.getString(key) ?: return null
        return Json.decodeFromString(serializer, jsonString)
    }

    /**
     * Puts the [Serializable] object into the [Bundle] with the given [key].
     *
     * @param bundle The [Bundle] to put the [Serializable] object into.
     * @param key The key associated with the [Serializable] object.
     * @param value The [Serializable] object to put into the [Bundle].
     */
    override fun put(bundle: Bundle, key: String, value: T) =
        bundle.putSerializable(key, value)

    /**
     * Parses a [Serializable] object from the given [value] string.
     *
     * @param value The string representation of the [Serializable] object.
     * @return The parsed [Serializable] object.
     */
    override fun parseValue(value: String): T = Json.decodeFromString(serializer, value)

    /**
     * Serializes the [Serializable] object into a string.
     *
     * @param value The [Serializable] object to serialize.
     * @return The string representation of the [Serializable] object.
     */
    override fun serializeAsValue(value: T): String = Json.encodeToString(serializer, value)

    /**
     * The name of the [Serializable] type.
     */
    override val name: String = clazz.name

    companion object {
        /**
         * Utility function to create a map of [KType] to [CustomNavType] for the given [Serializable] type and serializer.
         *
         * @param T The type of the [Serializable] object.
         * @param serializer The [KSerializer] for the [Serializable] type.
         * @return A map of [KType] to [CustomNavType].
         */
        inline fun <reified T : Serializable> getCustomNavTypeMap(serializer: KSerializer<T>): Map<KType, CustomNavType<T>> =
            mapOf(
                typeOf<T>() to CustomNavType(T::class.java, serializer),
            )
    }
}
