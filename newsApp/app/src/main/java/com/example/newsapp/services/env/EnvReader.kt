package com.example.newsapp.services.env

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.util.Properties

/**
 * A utility class that loads properties from a configuration file.
 *
 * This class is designed to read key-value pairs stored in a properties file within
 * the application's assets folder. It's typically used to manage environment-specific
 * configurations such as API endpoints, configuration settings, or any other parameters
 * that may vary between development, staging, and production environments.
 *
 * @property context the application context needed to access the assets. This is annotated
 * with @ApplicationContext to ensure that it refers to the context of the whole application,
 * not just a single component, thus avoiding memory leaks associated with the wrong use of contexts.
 * @constructor Creates an instance of EnvReader which will use the provided application context
 * to access the assets.
 */
class EnvReader (
    @ApplicationContext private val context: Context
){
    private val tag = "ENV_READER"

    /**
     * The name of the configuration file from which properties are loaded. Default is "env.properties".
     */
    var configFileName = "env.properties"

    /**
     * Loads the properties from the configuration file specified by [configFileName].
     *
     * This method will attempt to open and read the configuration file from the application's assets.
     * If the file is found, it will load the properties into a [Properties] object and return it.
     * If there are any issues during file access, such as the file not existing or read errors,
     * the method will catch an IOException, print the stack trace, and return an empty Properties object.
     *
     * @return a [Properties] object containing all key-value pairs from the configuration file.
     * If the file cannot be read, returns an empty Properties object.
     */
    fun loadProperties(): Properties {
        val properties = Properties()
        try {
            val inputStream = context.assets.open(configFileName)
            properties.load(inputStream)
            inputStream.close()
        } catch (e: IOException) {
            Log.e(tag, "loadProperties: ${e.message}", e)
        }
        return properties
    }
}