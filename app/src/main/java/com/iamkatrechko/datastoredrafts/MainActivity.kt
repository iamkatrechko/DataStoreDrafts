package com.iamkatrechko.datastoredrafts

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val scope = MainScope()
    private val handler = Handler(Looper.getMainLooper())
    private val preferencesDataStore by preferencesDataStore("PreferencesDataStoreFileName")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_pref_write).setOnClickListener {
            scope.launch {
                preferencesDataStore.edit { prefs ->
                    prefs[FIRST_PREFERENCE_KEY] = "Записанное значение preference"
                }
            }
        }
        findViewById<Button>(R.id.btn_pref_read).setOnClickListener {
            scope.launch {
                val value = preferencesDataStore.data.firstOrNull()?.get(FIRST_PREFERENCE_KEY) ?: "Пустое значение"
                handler.post { Toast.makeText(this@MainActivity, value, Toast.LENGTH_SHORT).show() }
            }
        }
    }

    companion object {

        private val FIRST_PREFERENCE_KEY = stringPreferencesKey("pref_key")
    }
}