package com.thienphu.mytodolistapp.repositories

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.thienphu.mytodolistapp.model.Priority
import com.thienphu.mytodolistapp.utils.Constants.PREFERENCE_KEY
import com.thienphu.mytodolistapp.utils.Constants.PREFERENCE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences

val Context.dataStore by preferencesDataStore(PREFERENCE_NAME)

class DataStoreRepository(context: Context) {


    private object PreferencesKeys {
        val sortState = stringPreferencesKey(PREFERENCE_KEY)
    }

    private val dataStore = context.dataStore

    suspend fun persistSortState(priority: Priority){
        dataStore.edit {
            preferences -> preferences[PreferencesKeys.sortState] = priority.name
        }
    }

    val readSortState: Flow<String> = dataStore.data
        .catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.sortState] ?: Priority.NONE.name
        }

}