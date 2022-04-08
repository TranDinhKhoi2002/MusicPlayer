package pj.tdk.musicplayer.repository

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import pj.tdk.musicplayer.models.Contributor

interface LocalDataRepository {
    fun contributors(): List<Contributor>
}

class RealLocalDataRepository(
    private val context: Context
) : LocalDataRepository {

    override fun contributors(): List<Contributor> {
        val jsonString = context.assets.open("contributors.json")
            .bufferedReader().use { it.readText() }

        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()
        val listContributorType = object : TypeToken<List<Contributor>>() {}.type
        return gson.fromJson(jsonString, listContributorType)
    }
}