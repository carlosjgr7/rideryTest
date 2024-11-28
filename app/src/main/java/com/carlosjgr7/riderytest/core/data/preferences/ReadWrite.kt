package com.carlosjgr7.riderytest.core.data.preferences

import kotlinx.coroutines.flow.Flow

interface ReadWrite<T> {
    suspend fun save(key: String, value: T?)
    suspend fun read(key: String): Flow<T?>
}