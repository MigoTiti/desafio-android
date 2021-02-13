package com.lucasrodrigues.apodnasa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg items: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(items: List<T>)

    @Update
    suspend fun update(item: T)
}