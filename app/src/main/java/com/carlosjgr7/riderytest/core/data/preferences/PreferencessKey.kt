package com.carlosjgr7.riderytest.core.data.preferences

data class PreferencesKey<T>(val key: String, val defaultValue: T) {
    companion object {
        val USER_TOKEN = PreferencesKey("token", null)
    }
}

