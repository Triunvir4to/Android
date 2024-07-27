package com.example.newsapp.utils

import android.content.Context
import java.io.IOException
import java.util.Properties

class EnvReader {
    var configFileName = "env.properties"

    fun loadProperties(context: Context): Properties {
        val properties = Properties()
        try {
            val inputStream = context.assets.open(configFileName)
            properties.load(inputStream)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return properties
    }
}