package com.example.notevault

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.notevault.data.UserPreferencesRepository


private const val TOKEN_PREFERENCES_NAME = "user_prefs"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = TOKEN_PREFERENCES_NAME
)

class NotesApplication : Application() {

    lateinit var userPreferencesRepository: UserPreferencesRepository
        private set

    override fun onCreate() {
        super.onCreate()

        userPreferencesRepository = UserPreferencesRepository(applicationContext.dataStore)
    }
}