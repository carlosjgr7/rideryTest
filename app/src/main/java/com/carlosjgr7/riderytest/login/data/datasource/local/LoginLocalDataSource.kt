package com.carlosjgr7.riderytest.login.data.datasource.local

import android.util.Log
import com.carlosjgr7.riderytest.core.data.preferences.LocalStorage
import com.carlosjgr7.riderytest.core.data.preferences.PreferencesKey
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor(
    private val localStorage: LocalStorage
) {
    suspend fun saveGoogleSession(account: GoogleSignInAccount) {
        localStorage.save(PreferencesKey.USER_TOKEN.key, account.email)
    }

    suspend fun logoutGoogleSession(): Boolean {
        try {
            localStorage.save(PreferencesKey.USER_TOKEN.key, PreferencesKey.USER_TOKEN.defaultValue)
            return true
        }catch(e: Exception) {
            return false
        }
    }


    suspend fun getGoogleSession(): Boolean {
        val data = localStorage.read(PreferencesKey.USER_TOKEN.key).first()
        return data != null
    }


}