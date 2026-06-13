package com.example.dailytip.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TipDao {

    @Query("SELECT * FROM tips ORDER BY dateAdded DESC")
    fun getAllTips(): Flow<List<TipEntity>>

    @Query("SELECT * FROM tips")
    suspend fun getAllTipsSnapshot(): List<TipEntity>

    @Query("SELECT * FROM tips WHERE id = :id")
    suspend fun getTipById(id: Long): TipEntity?

    @Query("SELECT COUNT(*) FROM tips")
    suspend fun getTipCount(): Int

    @Insert
    suspend fun insertTip(tip: TipEntity): Long

    @Insert
    suspend fun insertAll(tips: List<TipEntity>)

    @Delete
    suspend fun deleteTip(tip: TipEntity)
}
